package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.InsufficientStockException;
import com.progra3.cafeteria_api.model.entity.*;
import com.progra3.cafeteria_api.service.IStockService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService implements IStockService {
    private final ProductService productService;
    private final ProductOptionService productOptionService;

    @Transactional
    @Override
    public void decreaseStockForItem(Item item) {
        Product product = item.getProduct();
        int quantity = item.getQuantity();

        if(product.isControlStock()) {
            checkStockAvailability(item);
        }

        switch (product.getCompositionType()) {
            case NONE -> decreaseStock(product, quantity);

            case SELECTABLE -> {
                for (SelectedProductOption selectedOption : item.getSelectedOptions()) {
                    ProductOption option = productOptionService.getEntityById(selectedOption.getProductOption().getId());
                    decreaseStock(option.getProduct(), selectedOption.getQuantity() * quantity);
                }
            }

            case FIXED -> {
                for (ProductComponent component : product.getComponents()) {
                    decreaseStock(component.getProduct(), component.getQuantity() * quantity);
                }
            }

            case FIXED_SELECTABLE -> {
                for (ProductComponent component : product.getComponents()) {
                    decreaseStock(component.getProduct(), component.getQuantity() * quantity);
                }
                for (SelectedProductOption selectedOption : item.getSelectedOptions()) {
                    ProductOption option = productOptionService.getEntityById(selectedOption.getProductOption().getId());
                    decreaseStock(option.getProduct(), selectedOption.getQuantity() * quantity);
                }
            }
        }
    }

    @Transactional
    @Override
    public void increaseStock(Product product, int quantity) {
        product.setStock(product.getStock() + quantity);
    }

    private void checkStockAvailability(Item item) {
        Product product = item.getProduct();
        int quantity = item.getQuantity();


        switch (product.getCompositionType()) {
            case NONE -> checkStockAvailable(product.getId(), quantity);

            case SELECTABLE -> {
                for (SelectedProductOption selectedOption : item.getSelectedOptions()) {
                    ProductOption option = productOptionService.getEntityById(selectedOption.getProductOption().getId());
                    checkStockAvailable(option.getProduct().getId()
                            , selectedOption.getQuantity() * quantity);
                }
            }

            case FIXED -> {
                for (ProductComponent component : product.getComponents()) {
                    checkStockAvailable(component.getProduct().getId(),
                            component.getQuantity() * quantity);
                }
            }

            case FIXED_SELECTABLE -> {
                for (ProductComponent component : product.getComponents()) {
                    checkStockAvailable(component.getProduct().getId(),
                            component.getQuantity() * quantity);
                }
                for (SelectedProductOption selectedOption : item.getSelectedOptions()) {
                    ProductOption option = productOptionService.getEntityById(selectedOption.getProductOption().getId());
                    checkStockAvailable(option.getProduct().getId()
                            , selectedOption.getQuantity() * quantity);
                }
            }
        }
    }

    private void checkStockAvailable(Long productId, int requiredQuantity) {
        int currentStock = productService.getEntityById(productId).getStock();
        if (currentStock < requiredQuantity) {
            throw new InsufficientStockException(productId);
        }
    }

    private void decreaseStock(Product product, int quantity) {
        if(product.isControlStock()) {
            checkStockAvailable(product.getId(), quantity);
            product.setStock(product.getStock() - quantity);
            productService.updateProduct(product);
        }
    }
}

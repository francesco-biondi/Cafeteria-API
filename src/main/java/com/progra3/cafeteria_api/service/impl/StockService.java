package com.progra3.cafeteria_api.service.impl;

import com.progra3.cafeteria_api.exception.InsufficientStockException;
import com.progra3.cafeteria_api.model.entity.Item;
import com.progra3.cafeteria_api.model.entity.Product;
import com.progra3.cafeteria_api.model.entity.ProductComponent;
import com.progra3.cafeteria_api.model.entity.SelectedProductOption;
import com.progra3.cafeteria_api.service.IStockService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService implements IStockService {
    private final ProductService productService;

    @Transactional
    @Override
    public void decreaseStockForItem(Item item) {
        Product product = item.getProduct();
        int quantity = item.getQuantity();

        checkStockAvailability(item);

        switch (product.getCompositionType()) {
            case NONE -> decreaseStock(product, quantity);

            case SELECTABLE -> {
                for (SelectedProductOption option : item.getSelectedOptions()) {
                    decreaseStock(option.getProductOption().getProduct(), option.getQuantity() * quantity);
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
                for (SelectedProductOption option : item.getSelectedOptions()) {
                    decreaseStock(option.getProductOption().getProduct(), option.getQuantity() * quantity);
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
            case NONE -> checkStockAvailable(product, quantity);

            case SELECTABLE -> {
                for (SelectedProductOption option : item.getSelectedOptions()) {
                    checkStockAvailable(option.getProductOption().getProduct()
                            , option.getQuantity() * quantity);
                }
            }

            case FIXED -> {
                for (ProductComponent component : product.getComponents()) {
                    checkStockAvailable(component.getProduct(),
                            component.getQuantity() * quantity);
                }
            }

            case FIXED_SELECTABLE -> {
                for (ProductComponent component : product.getComponents()) {
                    checkStockAvailable(component.getProduct(),
                            component.getQuantity() * quantity);
                }
                for (SelectedProductOption option : item.getSelectedOptions()) {
                    checkStockAvailable(option.getProductOption().getProduct(),
                            option.getQuantity() * quantity);
                }
            }
        }
    }

    private void checkStockAvailable(Product product, int requiredQuantity) {
        int currentStock = productService.getEntityById(product.getId()).getStock();
        if (currentStock < requiredQuantity) {
            throw new InsufficientStockException(product.getId());
        }
    }

    private void decreaseStock(Product product, int quantity) {
        checkStockAvailable(product, quantity);
        product.setStock(product.getStock() - quantity);
        productService.updateProduct(product);
    }
}

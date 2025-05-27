package com.progra3.cafeteria_api.service;

import com.progra3.cafeteria_api.exception.product.NotEnoughStockException;
import com.progra3.cafeteria_api.model.entity.Product;

public class StockService implements IStockService{
    @Override
    public void decreaseStock(Product product, int quantity) {
        checkStockAvailability(product, quantity);
        product.setStock(product.getStock() - quantity);
    }

    @Override
    public void increaseStock(Product product, int quantity) {
        product.setStock(product.getStock() + quantity);
    }

    @Override
    public void decreaseStockForComposite(Product compositeProduct, int quantity) {
        compositeProduct.setStock(compositeProduct.getStock() - quantity);
    }

    @Override
    public void increaseStockForComposite(Product compositeProduct, int quantity) {

    }

    private void checkStockAvailability(Product product, int quantity) {
        if (product.getStock() < quantity) {
            throw new NotEnoughStockException(product.getId());
        }
    }

    private void checkStockAvailabilityForComposite(Product compositeProduct, int quantity) {
        compositeProduct.getComponents().forEach(component -> {
            checkStockAvailability(component.getProduct(), component.getQuantity() * quantity);
        });
    }
}

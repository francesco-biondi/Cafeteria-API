package com.progra3.cafeteria_api.service;

import com.progra3.cafeteria_api.model.entity.Product;

public interface IStockService {
    void decreaseStock(Product product, int quantity);

    void increaseStock(Product product, int quantity);

    void decreaseStockForComposite(Product compositeProduct, int quantity);

    void increaseStockForComposite(Product compositeProduct, int quantity);
}

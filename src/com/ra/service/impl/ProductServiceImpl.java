package com.ra.service.impl;


import com.ra.entity.model.Product;

import java.util.List;

public interface ProductServiceImpl {
    void saveToFile(List<Product> list);
    List<Product> getAllToFile();
    void addProduct();
    void showProduct();
    void updateProduct() throws Exception;
    void deleteProduct();
    void searchProduct();
    void sortByName();
    void sortByProfit();
}

package com.ra.service.impl;

import com.ra.entity.model.Category;

import java.util.List;
import java.util.function.Predicate;

public interface CategoryServiceImpl {
    void saveToFile(List<Category> list);
    List<Category> getAllToFile();
    void addCategory() throws Exception;
    void showListCategory();
    void updateCategory() throws Exception;
    boolean findAny(Predicate<Category> predicate);
    Category findByCategoryCodeInListCategory(Integer categoryCode,List<Category> category);
    void findByCategoryNameInListCategory(String categoryName);
    void deleteCategory();
    void searchCategory();
    void sortCategory();
    void statisticCategory();
}

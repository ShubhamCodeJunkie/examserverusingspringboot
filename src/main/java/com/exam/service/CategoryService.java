package com.exam.service;

import com.exam.model.exam.Category;

import java.util.Set;

public interface CategoryService {

    public Category addCategory(Category category);
    public Category UpdateCategory(Category category);
    public Set<Category> getCategories();
    public Category getCategory(Long categorId);
    public void deleteCategory(Long categoryId);

}

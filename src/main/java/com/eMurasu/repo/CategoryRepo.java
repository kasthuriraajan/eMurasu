package com.eMurasu.repo;

import com.eMurasu.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepo extends CrudRepository<Category, Integer> {
    Category findById(int categoryId);
}

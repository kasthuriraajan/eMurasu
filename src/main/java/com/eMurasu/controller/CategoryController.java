package com.eMurasu.controller;


import com.eMurasu.Objects.ReturnMessage;
import com.eMurasu.enums.BasicDataState;
import com.eMurasu.model.Category;
import com.eMurasu.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

    @Autowired
    private CategoryRepo categoryRepo;

    @PostMapping(path = "/addCategory",produces = "application/json")
    public ReturnMessage addCategory(@RequestBody Category category){
        try{
            categoryRepo.save(category);
            return new ReturnMessage("You have added a Category successfully");
        }
        catch (Exception e){
            return new ReturnMessage(e.getMessage());
        }
    }

    @PostMapping(path = "/deactivateCategory", produces = "application/json")
    public ReturnMessage deactivateCategory(@RequestParam int categoryId){
        try {
            Category category = categoryRepo.findById(categoryId);
            category.setStatus(BasicDataState.OBSOLETE);
            categoryRepo.save(category);
            return new ReturnMessage("You have deactivated the Category - "+category.getTitle() +" successfully!");
        }
        catch (Exception e){
            return new ReturnMessage(e.getMessage());
        }
    }


    @GetMapping(path = "/categories",produces = "application/json")
    public Iterable<Category> categories(){
        return categoryRepo.findAll();
    }

    @GetMapping(path = "/getCategory",produces = "application/json")
    public Category getCategory(@RequestParam(value = "categoryId") int categoryId){
        return categoryRepo.findById(categoryId);
    }
}

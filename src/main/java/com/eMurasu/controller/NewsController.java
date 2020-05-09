package com.eMurasu.controller;

import com.eMurasu.Objects.NewsDetails;
import com.eMurasu.Objects.ReturnMessage;
import com.eMurasu.enums.NewsState;
import com.eMurasu.model.News;
import com.eMurasu.repo.CategoryRepo;
import com.eMurasu.repo.NewsRepo;
import com.eMurasu.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Date;

@RestController
public class NewsController {

    @Autowired
    private NewsRepo newsRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private UserRepo userRepo;

    @PostMapping(path = "/createNews", produces = "application/json")
    public ReturnMessage createNews(@RequestBody NewsDetails newsDetails){
        try {
            News news = new News();
            news.setTitle(newsDetails.getTitle());
            news.setDescription(newsDetails.getDescription());
            if(categoryRepo.existsById(newsDetails.getCategory())){
                news.setCategory(newsDetails.getCategory());
            }
            else {
                return new ReturnMessage("Category Id "+ newsDetails.getCategory()+" is not exists please try with a " +
                        "valid CategoryId.");
            }
            news.setContent(newsDetails.getContent());
            news.setCreatedBy(2);
            news.setCreatedAt(new Timestamp(new Date().getTime()));
            news.setPriority(newsDetails.getPriority());
            news.setStatus(NewsState.STARTED);

            newsRepo.save(news);
            return new ReturnMessage("YOu have created a news successfully");

        }
        catch (Exception e){
            return new ReturnMessage(e.getMessage());
        }
    }

    @GetMapping(path = "/news",produces = "application/json")
    public Iterable<News> news(){
        return newsRepo.findAll();
    }

    @GetMapping(path = "/getNews",produces = "application/json")
    public News getNews(@RequestParam(value = "newsId") int newsId){
        return newsRepo.findById(newsId);
    }
}

package com.eMurasu.controller;

import com.eMurasu.Objects.MediaDetails;
import com.eMurasu.Objects.ReturnMessage;
import com.eMurasu.enums.MediaState;
import com.eMurasu.model.Media;
import com.eMurasu.model.MediaType;
import com.eMurasu.model.User;
import com.eMurasu.repo.MediaRepo;
import com.eMurasu.repo.MediaTypeRepo;
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
public class MediaController {
    @Autowired
    private MediaRepo mediaRepo;
    @Autowired
    private MediaTypeRepo mediaTypeRepo;
    @Autowired
    private UserRepo userRepo;

    @PostMapping(path = "/addMedia", produces = "application/json")
    public ReturnMessage addMedia(@RequestBody MediaDetails mediaDetails){
        try {
            User createdBy = userRepo.findById(2);
            if (mediaDetails.getOwner().equals(null)||mediaDetails.getOwner().equals("")) {
                mediaDetails.setOwner(createdBy.getUsername());
            }
            Media media = new Media();
            if (mediaTypeRepo.existsById(mediaDetails.getType())) {
                media.setType(mediaDetails.getType());
            }
            else {
                return new ReturnMessage("MediaTypeId "+ mediaDetails.getType()+" is not exist. Please try with a " +
                        "valid MediaTypeId");
            }
            media.setCaption(mediaDetails.getCaption());
            media.setDescription(mediaDetails.getDescription());
            media.setUrl(mediaDetails.getUrl());
            media.setOwner(mediaDetails.getOwner());
            media.setUploadedBy(createdBy.getId());
            media.setUploadedAt(new Timestamp(new Date().getTime()));
            media.setStatus(MediaState.NEW);
            mediaRepo.save(media);
            return new ReturnMessage("You have added a Media successfully");
        }
        catch (Exception e){
            return new ReturnMessage(e.getMessage());
        }
    }

    @GetMapping(path = "/medias",produces = "application/json")
    public Iterable<Media> medias(){
        return mediaRepo.findAll();
    }

    @GetMapping(path = "/getMedia",produces = "application/json")
    public Media getMedia(@RequestParam(value = "mediaId") int mediaId){
        return mediaRepo.findById(mediaId);
    }
}

package com.eMurasu.controller;

import com.eMurasu.Objects.ReturnMessage;
import com.eMurasu.enums.BasicDataState;
import com.eMurasu.model.MediaType;
import com.eMurasu.repo.MediaTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MediaTypeController {
    @Autowired
    private MediaTypeRepo mediaTypeRepo;

    @PostMapping(path = "/addMediaType",produces = "application/json")
    public ReturnMessage addMediaType(@RequestBody MediaType mediaType){
        try{
            mediaTypeRepo.save(mediaType);
            return new ReturnMessage("You have added a Media Type successfully!");
        }
        catch (Exception e){
            return new ReturnMessage(e.getMessage());
        }
    }

    @PostMapping(path = "/deactivateMediaType", produces = "application/json")
    public ReturnMessage deactivateMediaType(@RequestParam int mediaTypeId){
        try {
            MediaType mediaType = mediaTypeRepo.findById(mediaTypeId);
            mediaType.setStatus(BasicDataState.OBSOLETE);
            mediaTypeRepo.save(mediaType);
            return new ReturnMessage("You have deactivated the Media Type - "+mediaType.getTitle() +" successfully!");
        }
        catch (Exception e){
            return new ReturnMessage(e.getMessage());
        }
    }

    @GetMapping(path = "/mediaTypes",produces = "application/json")
    public Iterable<MediaType> mediaTypes(){
        return mediaTypeRepo.findAll();
    }

    @GetMapping(path = "/getMediaType",produces = "application/json")
    public MediaType getMediaType(@RequestParam(value = "mediaTypeId") int mediaTypeId){
        return mediaTypeRepo.findById(mediaTypeId);
    }
}

package com.eMurasu.repo;

import com.eMurasu.model.MediaType;
import org.springframework.data.repository.CrudRepository;

public interface MediaTypeRepo extends CrudRepository<MediaType, Integer> {
    MediaType findById(int mediaTypeId);
}

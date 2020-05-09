package com.eMurasu.repo;

import com.eMurasu.model.Media;
import org.springframework.data.repository.CrudRepository;

public interface MediaRepo extends CrudRepository<Media, Integer> {
    Media findById(int mediaId);
}

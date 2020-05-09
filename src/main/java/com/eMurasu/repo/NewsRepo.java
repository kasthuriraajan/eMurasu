package com.eMurasu.repo;

import com.eMurasu.model.News;
import org.springframework.data.repository.CrudRepository;

public interface NewsRepo extends CrudRepository<News, Integer> {
    News findById(int newsId);
}

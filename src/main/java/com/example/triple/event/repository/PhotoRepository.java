package com.example.triple.event.repository;


import com.example.triple.event.model.Photo;
import com.example.triple.event.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    @Transactional
    void deleteAllByReview(Review review);
}

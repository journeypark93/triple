package com.example.triple.event.repository;


import com.example.triple.event.model.Photo;
import com.example.triple.event.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {

    List<Photo> findAllByReview(Review review);
}

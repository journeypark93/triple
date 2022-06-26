package com.example.triple.event.repository;


import com.example.triple.event.model.Place;
import com.example.triple.event.model.Review;
import com.example.triple.event.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Review findByUserAndPlace(User user, Place place);

    Review findByReviewId(String reviewId);

    @Transactional
    void deleteByReviewId(String reviewId);
}

package com.example.triple.mockobject.Repository;

import com.example.triple.event.model.Photo;
import com.example.triple.event.model.Place;
import com.example.triple.event.model.Review;
import com.example.triple.event.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MockReviewRepository {

    private List<Review> reviews = new ArrayList<>();
    // 리뷰 테이블 ID: 1부터 시작
    private Long id = 1L;

    // 리뷰 저장
    public Review save(Review review) {

        // 신규 리뷰 -> DB 에 저장
        review.setId(id);
        ++id;
        reviews.add(review);
        return review;
    }

    // 리뷰 ID 로 리뷰 조회
    public Optional<Review> findById(Long id) {
        for (Review review : reviews) {
            if (review.getId().equals(id)) {
                return Optional.of(review);
            }
        }
        return Optional.empty();
    }

    // 회원 ID 로 등록된 상품 조회
    public List<Review> findAllByUserId(Long userId) {
        List<Review> userReviews = new ArrayList<>();
        for (Review review : reviews) {
            if (review.getId().equals(userId)) {
                userReviews.add(review);
            }
        }
        return userReviews;
    }

    // 리뷰 전체 조회
    public List<Review> findAll() {
        return reviews;
    }

    public Review findByUserAndPlace(User user, Place place) {
        for(Review review:reviews){
            if(review.getUser().equals(user)&&review.getPlace().equals(place)){
                return review;
            }
        }
        return null;
    }

    public Review findByReviewId(String reviewId) {
        for(Review review:reviews){
            if(review.getReviewId().equals(reviewId)){
                return review;
            }
        }
        return null;
    }

    public void deleteByReviewId(String reviewId) {
        reviews.removeIf(review -> review.getReviewId().equals(reviewId));
    }

    public void savePhotos(Review userReview, List<Photo> attachedPhotoIds) {
        for(Review review:reviews){
            if(review == userReview){
                review.setPhotos(attachedPhotoIds);
            }
        }
    }
}

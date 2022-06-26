package com.example.triple.event.service;

import com.example.triple.event.dto.ReviewRequestDto;
import com.example.triple.event.model.*;
import com.example.triple.event.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private static final String type = "REVIEW";
    private static final String addReview = "ADD";
    private static final String modReview = "MOD";
    private static final String deleteReview = "DELETE";

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final PhotoRepository photoRepository;
    private final PointRepository pointRepository;

    @Transactional
    public String createEvent(ReviewRequestDto reviewRequestDto) {
        if (reviewRequestDto.getType().equals(type)) {
            switch (reviewRequestDto.getAction()) {
                case addReview:
                    //리뷰 등록
                    return registerReview(reviewRequestDto);

                case modReview:
                    //리뷰 수정
                    return updateReview(reviewRequestDto);

                case deleteReview:
                    //리뷰 삭제
                    return removeReview(reviewRequestDto);
            }
        } else {
            throw new IllegalArgumentException("Type이 일치하지 않습니다.");
        }
        return "";
    }

    //리뷰 등록
    private String registerReview(ReviewRequestDto reviewRequestDto) {
        //글과 사진 모두 첨부되어 있지 않을 때, exception 발생한다고 가정
        if(reviewRequestDto.getContent().length()==0 && reviewRequestDto.getAttachedPhotoIds().size()==0){
            throw new IllegalArgumentException("글과 이미지가 모두 비어있습니다.");
        }
        //회원가입하여 등록된 user가 있다고 가정
        User user = userRepository.findByUsername(reviewRequestDto.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("저장된 User가 없습니다.");
        }
        //이미 저장된 place id가 있다고 가정
        Place place = placeRepository.findByPlaceName(reviewRequestDto.getPlaceId());
        if (place == null) {
            throw new IllegalArgumentException("잘못된 장소입니다.");
        }
        //이미 등록된 리뷰가 있는지 확인
        Review review = reviewRepository.findByUserAndPlace(user, place);
        if (review == null) {
            //포인트 산정
            long points = 0L;
            if (reviewRequestDto.getContent().length() > 0) {
                ++points;
            }
            if (reviewRequestDto.getAttachedPhotoIds().size() > 0) {
                ++points;
            }
            if (place.getReviews().isEmpty()) {
                ++points;
            }
            //리뷰 등록
            Review userReview = new Review(reviewRequestDto, user, place, points);
            reviewRepository.save(userReview);
            //포인트 로그 등록
            pointRepository.save(new Point(points, user));
            //사진 id 등록
            for (String photoId : reviewRequestDto.getAttachedPhotoIds()) {
                Photo photo = new Photo(photoId, userReview);
                photoRepository.save(photo);
            }
        } else {
            throw new IllegalArgumentException("이미 리뷰가 등록되었습니다.");
        }

        return "리뷰등록 완료";
    }

    //리뷰 수정
    private String updateReview(ReviewRequestDto reviewRequestDto) {
        //등록된 리뷰 조회
        Review review = reviewRepository.findByReviewName(reviewRequestDto.getReviewId());
        //리뷰 validation
        if (review == null) {
            throw new IllegalArgumentException("리뷰가 등록되어있지 않습니다.");
        }
        if (!reviewRequestDto.getUserId().equals(review.getUser().getUsername())) {
            throw new IllegalArgumentException("등록한 사용자와 일치하지 않아 수정할 수 없습니다.");
        }
        if (!reviewRequestDto.getPlaceId().equals(review.getPlace().getPlaceName())) {
            throw new IllegalArgumentException("등록한 리뷰의 장소와 수정하는 장소가 일치하지 않습니다.");
        }
        //포인트 산정
        long points = review.getPoints();
        long earnPoits = 0L;
        if (review.getPhotos().isEmpty() && reviewRequestDto.getAttachedPhotoIds().size() > 0) {
            ++earnPoits;
        }
        if (review.getContent().length()>0 && review.getPhotos().size()>0 && reviewRequestDto.getAttachedPhotoIds().size()==0) {
            --earnPoits;
        }
        //리뷰 업데이트 저장
        List<Photo> photos = new ArrayList<>();
        for (String photoId : reviewRequestDto.getAttachedPhotoIds()) {
            Photo photo = new Photo(photoId, review);
            photos.add(photo);
        }
        photoRepository.deleteAllByReview(review);
        photoRepository.saveAll(photos);

        review.update(reviewRequestDto, points+earnPoits);
        //포인트 로그 등록
        pointRepository.save(new Point(earnPoits, review.getUser()));

        return "리뷰수정 완료";
    }

    //리뷰 삭제
    private String removeReview(ReviewRequestDto reviewRequestDto) {
        Review review = reviewRepository.findByReviewName(reviewRequestDto.getReviewId());
        long points = -1 * review.getPoints();
        if(points!=0){
            pointRepository.save(new Point(points, review.getUser()));
        }
        reviewRepository.deleteByReviewName(review.getReviewName());
        return "리뷰삭제 완료";
    }
}



package com.example.triple.mockobject.Service;

import com.example.triple.event.dto.ReviewRequestDto;
import com.example.triple.event.model.*;
import com.example.triple.mockobject.Repository.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public class MockReviewService {

    private static final String type = "REVIEW";
    private static final String addReview = "ADD";
    private static final String modReview = "MOD";
    private static final String deleteReview = "DELETE";

    private final MockReviewRepository mockReviewRepository;
    private final MockUserRepository mockUserRepository;
    private final MockPlaceRepository mockPlaceRepository;
    private final MockPhotoRepository mockPhotoRepository;
    private final MockPointRepository mockPointRepository;

    public MockReviewService(){
        this.mockReviewRepository = new MockReviewRepository();
        this.mockUserRepository = new MockUserRepository();
        this.mockPlaceRepository = new MockPlaceRepository();
        this.mockPhotoRepository = new MockPhotoRepository();
        this.mockPointRepository = new MockPointRepository();
    }



    @Transactional
    public String createEvent(ReviewRequestDto reviewRequestDto) {
        //미리 user, place 저장
        mockUserRepository.save(reviewRequestDto.getUserId());
        mockPlaceRepository.save(reviewRequestDto.getPlaceId());
        //리뷰 등록, 수정, 삭제
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
        //회원가입 시, 등록된 user가 있다고 가정
        User user = mockUserRepository.findByUsername(reviewRequestDto.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("저장된 User가 없습니다.");
        }
        //이미 저장된 place id가 있다고 가정
        Place place = mockPlaceRepository.findByPlaceName(reviewRequestDto.getPlaceId());
        if (place == null) {
            throw new IllegalArgumentException("잘못된 장소입니다.");
        }
        //이미 등록된 리뷰가 있는지 확인
        Review review = mockReviewRepository.findByUserAndPlace(user, place);
        if (review == null) {
            //포인트 산정
            long points = 0L;
            if (reviewRequestDto.getContent().length() > 0) {
                ++points;
            }
            if (reviewRequestDto.getAttachedPhotoIds().size() > 0) {
                ++points;
            }
            if (place.getReviews()==null) {
                ++points;
            }
            //리뷰 등록
            Review userReview = new Review(reviewRequestDto, user, place, points);
            mockReviewRepository.save(userReview);
            //포인트 로그 등록
            mockPointRepository.save(new Point(points, user));

            //사진 id 등록
            List<Photo> photos = new ArrayList<>();
            for (String photoId : reviewRequestDto.getAttachedPhotoIds()) {
                Photo photo = new Photo(photoId, userReview);
                mockPhotoRepository.save(photo);
                photos.add(photo);
            }
            //연관관계 맵핑위해 따로 추가로 저장
            mockReviewRepository.savePhotos(userReview, photos);
        } else {
            throw new IllegalArgumentException("이미 리뷰가 등록되었습니다.");
        }

        return "리뷰등록 완료";
    }

    //리뷰 수정
    private String updateReview(ReviewRequestDto reviewRequestDto) {
        //등록된 리뷰 조회
        Review review = mockReviewRepository.findByReviewId(reviewRequestDto.getReviewId());
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
        if (review.getPhotos()==null && reviewRequestDto.getAttachedPhotoIds().size() > 0) {
            ++points;
        }
        if (review.getContent().length()>0 && review.getPhotos().size()>0 && reviewRequestDto.getAttachedPhotoIds().size()==0) {
            --points;
        }
        //리뷰 업데이트 저장
        List<Photo> photos = new ArrayList<>();
        for (String photoId : reviewRequestDto.getAttachedPhotoIds()) {
            Photo photo = new Photo(photoId, review);
            photos.add(photo);
        }
        mockPhotoRepository.deleteAllByReview(review);
        mockPhotoRepository.saveAll(photos);

        review.update(reviewRequestDto, points);
        //포인트 로그 등록
        mockPointRepository.save(new Point(points, review.getUser()));

        return "리뷰수정 완료";
    }

    //리뷰 삭제
    private String removeReview(ReviewRequestDto reviewRequestDto) {
        Review review = mockReviewRepository.findByReviewId(reviewRequestDto.getReviewId());
        long points = -1 * review.getPoints();
        if(points!=0){
            mockPointRepository.save(new Point(points, review.getUser()));
        }
        mockReviewRepository.deleteByReviewId(review.getReviewName());
        return "리뷰삭제 완료";
    }
}

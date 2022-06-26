package com.example.triple.mockobject.Service;

import com.example.triple.event.dto.PointResponseDto;
import com.example.triple.event.dto.ReviewRequestDto;
import com.example.triple.event.model.Place;
import com.example.triple.event.model.Point;
import com.example.triple.event.model.Review;
import com.example.triple.event.model.User;
import com.example.triple.mockobject.Repository.MockPlaceRepository;
import com.example.triple.mockobject.Repository.MockPointRepository;
import com.example.triple.mockobject.Repository.MockReviewRepository;
import com.example.triple.mockobject.Repository.MockUserRepository;

import java.util.List;

public class MockPointService {

    private final MockUserRepository mockUserRepository;
    private final MockPointRepository mockPointRepository;
    private final MockPlaceRepository mockPlaceRepository;
    private final MockReviewRepository mockReviewRepository;

    public MockPointService(){
        this.mockUserRepository = new MockUserRepository();
        this.mockPointRepository = new MockPointRepository();
        this.mockPlaceRepository = new MockPlaceRepository();
        this.mockReviewRepository = new MockReviewRepository();
    }

    public PointResponseDto getPoint(ReviewRequestDto reviewRequestDto) {
        //미리 review 저장
        User reviewUser = mockUserRepository.save(reviewRequestDto.getUserId());
        Place place = mockPlaceRepository.save(reviewRequestDto.getPlaceId());

        Review review = new Review(reviewRequestDto, reviewUser, place, 2L);
        mockReviewRepository.save(review);
        mockPointRepository.save(new Point(2L, reviewUser));

        Long pointSum = 0L;
        User user = mockUserRepository.findByUsername(reviewRequestDto.getUserId());
        if(user==null){
            throw new IllegalArgumentException("해당하는 user 가 없습니다.");
        }
        List<Point> points = mockPointRepository.findAllByUser(user);
        for(Point point:points){
            pointSum += point.getPoints();
        }
        return new PointResponseDto(pointSum);
    }
}

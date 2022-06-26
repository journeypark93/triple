package com.example.triple.event.service;

import com.example.triple.event.dto.PointResponseDto;
import com.example.triple.event.dto.ReviewRequestDto;
import com.example.triple.event.model.Point;
import com.example.triple.event.model.User;
import com.example.triple.event.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {

    private final UserRepository userRepository;
    private final PointRepository pointRepository;

    public PointResponseDto getPoint(ReviewRequestDto reviewRequestDto) {
        Long pointSum = 0L;
        User user = userRepository.findByUsername(reviewRequestDto.getUserId());
        List<Point> points = pointRepository.findAllByUser(user);
        for(Point point:points){
            pointSum += point.getPoints();
        }
        return new PointResponseDto(pointSum);
    }
}



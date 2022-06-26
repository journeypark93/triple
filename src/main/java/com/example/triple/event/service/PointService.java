package com.example.triple.event.service;

import com.example.triple.event.dto.PointResponseDto;
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

    public PointResponseDto getPoint(String userId) {
        Long pointSum = 0L;
        User user = userRepository.findByUsername(userId);
        if(user==null){
            throw new IllegalArgumentException("해당하는 user 가 없습니다.");
        }
        List<Point> points = pointRepository.findAllByUser(user);
        for(Point point:points){
            pointSum += point.getPoints();
        }
        return new PointResponseDto(pointSum);
    }
}



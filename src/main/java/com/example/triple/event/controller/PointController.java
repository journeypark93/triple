package com.example.triple.event.controller;

import com.example.triple.event.dto.PointResponseDto;
import com.example.triple.event.dto.ReviewRequestDto;
import com.example.triple.event.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    //개인 전체 포인트 조회(포인트 조회에 대한 request format이 없어 post/events 시 요청받은 data로 조회함.)
    @GetMapping(value = "/points")
    public ResponseEntity<PointResponseDto> getPoint(ReviewRequestDto reviewRequestDto) {
        PointResponseDto points = pointService.getPoint(reviewRequestDto);
        return ResponseEntity.ok()
                .body(points);
    }

}

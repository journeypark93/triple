package com.example.triple.event.controller;

import com.example.triple.event.dto.PointResponseDto;
import com.example.triple.event.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    //개인 전체 포인트 조회(포인트 조회에 대한 request format이 없어 queryString 으로 userId를 받아 조회함.)
    @GetMapping(value = "/points")
    public ResponseEntity<PointResponseDto> getPoint(@RequestParam(value = "userId") String userId) {
        PointResponseDto points = pointService.getPoint(userId);
        return ResponseEntity.ok()
                .body(points);
    }
}

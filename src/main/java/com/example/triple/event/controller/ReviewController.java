package com.example.triple.event.controller;

import com.example.triple.event.dto.ReviewRequestDto;
import com.example.triple.event.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    //리뷰 등록, 수정, 삭제
    @PostMapping(value = "/events")
    public ResponseEntity<String> createEvent(@RequestBody ReviewRequestDto reviewRequestDto) {
        String result = reviewService.createEvent(reviewRequestDto);
        return ResponseEntity.ok()
                .body(result);
    }
}

package com.example.triple.mockobject.Test;

import com.example.triple.event.dto.PointResponseDto;
import com.example.triple.event.dto.ReviewRequestDto;
import com.example.triple.mockobject.Service.MockPointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MockPointServiceTest {

    @Nested
    @DisplayName("회원 포인트 조회")
    class CreateUserReview {

        private String type;
        private String action;
        private String reviewId;
        private String content;
        private List<String> attachedPhotoIds;
        private String userId;
        private String placeId;

        @BeforeEach
        void setup() {
            type = "REVIEW";
            action = "ADD";
            reviewId = "240a0658-dc5f-4878-9381-ebb7b2667772";
            content = "좋아요!";
            attachedPhotoIds = new ArrayList<>();
            attachedPhotoIds.add("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8");
            attachedPhotoIds.add("afb0cef2-851d-4a50-bb07-9cc15cbdc332");
            userId = "3ede0ef2-92b7-4817-a5f3-0c575361f745";
            placeId = "2e4baf1c-5acb-4efb-a1af-eddada31b00f";
        }



        @Test
        @DisplayName("회원 포인트 조회 - 정상케이스")
        void saveReview_Normal() {
            //given
            ReviewRequestDto reviewRequestDto = new ReviewRequestDto(
                    type,
                    action,
                    reviewId,
                    content,
                    attachedPhotoIds,
                    userId,
                    placeId
            );

            MockPointService mockPointService = new MockPointService();
            //when
            PointResponseDto pointResponseDto = mockPointService.getPoint(reviewRequestDto);

            //then
            assertEquals(2L, pointResponseDto.getPoints());
        }
    }
}

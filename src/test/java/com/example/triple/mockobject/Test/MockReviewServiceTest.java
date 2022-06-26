package com.example.triple.mockobject.Test;

import com.example.triple.event.dto.ReviewRequestDto;
import com.example.triple.mockobject.Service.MockReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MockReviewServiceTest {

    @Nested
    @DisplayName("회원이 요청한 리뷰 등록, 수정, 삭제")
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
        @DisplayName("리뷰 등록 - 정상케이스")
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

            MockReviewService mockReviewService = new MockReviewService();
            //when
            String result = mockReviewService.createEvent(reviewRequestDto);

            //then
            assertEquals("리뷰등록 완료", result);
        }

        @Test
        @DisplayName("리뷰 수정 - 정상케이스")
        void updateReview_Normal() {
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
            //리뷰 등록
            MockReviewService mockReviewService = new MockReviewService();
            mockReviewService.createEvent(reviewRequestDto);

            reviewRequestDto.setAction("MOD");
            reviewRequestDto.setContent("싫어요!");
            List<String> photos = new ArrayList<>();
            reviewRequestDto.setAttachedPhotoIds(photos);
            //when
            String result = mockReviewService.createEvent(reviewRequestDto);

            //then
            assertEquals("리뷰수정 완료", result);
        }

        @Test
        @DisplayName("리뷰 삭제 - 정상케이스")
        void deleteReview_Normal() {
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
            //리뷰 등록
            MockReviewService mockReviewService = new MockReviewService();
            mockReviewService.createEvent(reviewRequestDto);

            reviewRequestDto.setAction("DELETE");
            //when
            String result = mockReviewService.createEvent(reviewRequestDto);

            //then
            assertEquals("리뷰삭제 완료", result);
        }
    }
}

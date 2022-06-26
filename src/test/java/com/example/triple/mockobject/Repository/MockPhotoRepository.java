package com.example.triple.mockobject.Repository;

import com.example.triple.event.model.Photo;

import java.util.ArrayList;
import java.util.List;

public class MockPhotoRepository {

    private List<Photo> photos = new ArrayList<>();
    // 사진 테이블 ID: 1부터 시작
    private Long id = 1L;

    // 사진 저장
    public Photo save(Photo photo) {

        // 신규 사용자 -> DB 에 저장
        photo.setId(id);
        ++id;
        photos.add(photo);
        return photo;
    }
}

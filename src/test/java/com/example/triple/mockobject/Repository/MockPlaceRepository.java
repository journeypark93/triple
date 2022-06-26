package com.example.triple.mockobject.Repository;

import com.example.triple.event.model.Place;

import java.util.ArrayList;
import java.util.List;

public class MockPlaceRepository {

    private List<Place> places = new ArrayList<>();
    // 장소 테이블 ID: 1부터 시작
    private Long id = 1L;

    // 장소 저장
    public Place save(String placeId) {
        Place place = new Place();
        // 신규 사용자 -> DB 에 저장
        place.setId(id);
        ++id;
        place.setPlaceName(placeId);
        places.add(place);
        return place;
    }

    //장소아이디로 place 조회
    public Place findByPlaceName(String placeId) {
        for(Place place:places){
            if(place.getPlaceName().equals(placeId)){
                return place;
            }
        }
        return null;
    }
}

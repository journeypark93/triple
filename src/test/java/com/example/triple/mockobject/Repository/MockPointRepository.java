package com.example.triple.mockobject.Repository;

import com.example.triple.event.model.Point;
import com.example.triple.event.model.User;

import java.util.ArrayList;
import java.util.List;

public class MockPointRepository {

    private List<Point> points = new ArrayList<>();
    // 장소 테이블 ID: 1부터 시작
    private Long id = 1L;

    // 장소 저장
    public Point save(Point point) {

        // 포인트 -> DB 에 저장
        point.setId(id);
        ++id;
        points.add(point);
        return point;
    }

    public List<Point> findAllByUser(User user) {
        List<Point> userPoint = new ArrayList<>();
        for(Point point:points){
            if(point.getUser()==user){
                userPoint.add(point);
            }
        }
        return userPoint;
    }
}

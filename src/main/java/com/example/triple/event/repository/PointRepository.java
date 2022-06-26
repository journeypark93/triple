package com.example.triple.event.repository;


import com.example.triple.event.model.Point;
import com.example.triple.event.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long> {

    List<Point> findAllByUserId(Long id);

    List<Point> findAllByUser(User user);
}

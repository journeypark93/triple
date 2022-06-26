package com.example.triple.event.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Point {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;                         //index로 사용

    @Column
    private Long points;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    public Point(long points, User user) {
        this.points = points;
        this.user = user;
    }
}

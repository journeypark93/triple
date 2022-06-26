package com.example.triple.event.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Users") //h2 연결 종료 후에는 지우기
public class User {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;                         //index로 사용

    @Column(nullable = false, unique = true) //고유한 값
    private String username;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

    @OneToMany(mappedBy = "user")
    private List<Point> points;

}

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
public class Place {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;                         //index로 사용

    @Column(nullable = false, unique = true) //고유한 값
    private String placeName;

    @OneToMany(mappedBy = "place")
    private List<Review> reviews;
}

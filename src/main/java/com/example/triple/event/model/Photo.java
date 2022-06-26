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
public class Photo {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;                         //index로 사용

    @Column(nullable = false, unique = true) //고유한 값
    private String attachedPhoto;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Review review;

    public Photo(String photo, Review userReview) {
        this.attachedPhoto = photo;
        this.review = userReview;
    }
}

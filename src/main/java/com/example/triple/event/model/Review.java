package com.example.triple.event.model;

import com.example.triple.event.dto.ReviewRequestDto;
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
@Table(name = "USERS")
public class Review {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;                         //index로 사용

    @Column(nullable = false, unique = true) //고유한 값
    private String reviewId;

    @Column
    private String content;

    @Column
    private Long points;

    @OneToMany(mappedBy = "review")
    private List<Photo> photos;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Place place;

    public Review(ReviewRequestDto reviewRequestDto, User user, Place place, long points) {
        this.reviewId = reviewRequestDto.getReviewId();
        this.content = reviewRequestDto.getContent();
        this.points = points;
        this.user = user;
        this.place = place;
    }

    public void update(ReviewRequestDto reviewRequestDto, long points, List<Photo> photos) {
        this.content = reviewRequestDto.getContent();
        this.points = points;
        this.photos = photos;
    }
}

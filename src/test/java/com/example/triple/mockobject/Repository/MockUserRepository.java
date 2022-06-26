package com.example.triple.mockobject.Repository;

import com.example.triple.event.model.User;

import java.util.ArrayList;
import java.util.List;

public class MockUserRepository {

    private List<User> users = new ArrayList<>();
    // 리뷰 테이블 ID: 1부터 시작
    private Long id = 1L;

    // 사용자 저장
    public User save(String userId) {
        User user = new User();
        // 신규 사용자 -> DB 에 저장
        user.setId(id);
        ++id;
        user.setUsername(userId);
        users.add(user);
        return user;
    }

    //유저아이디로 user 조회
    public User findByUsername(String userId) {
        for(User user : users){
            if(user.getUsername().equals(userId)){
                return user;
            }
        }
        return null;
    }
}

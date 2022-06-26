package com.example.triple.testData;


import com.example.triple.event.model.Place;
import com.example.triple.event.model.User;
import com.example.triple.event.repository.PlaceRepository;
import com.example.triple.event.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class testDataRunner implements ApplicationRunner {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PlaceRepository placeRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 테스트 User 생성
        User user0= new User(1L, "3ede0ef2-92b7-4817-a5f3-0c575361f745");
        userRepository.save(user0);
        System.out.println("유저를 저장합니다.");
        // 테스트 place 생성
        Place place0 = new Place(1L, "2e4baf1c-5acb-4efb-a1af-eddada31b00f");
        System.out.println("장소를 저장합니다.");
        placeRepository.save(place0);
    }
}

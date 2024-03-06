package com.study.mvc.repository;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

// field 명과 component 명이 같으면 실행
// @component 대신 @Repository를 달아도 안에 @Component 있어서 똑같다 /즉 명칭만 다른 Component 다 / 자동적으로 ioc container에 담긴다
@Repository("a")
public class CarRepositoryImpl implements CarRepository{

    @Override
    public List<String> getCarNames() {
        return List.of("아벤떼", "소나타");
    }

    @Override
    public int insertCar(String carName) {
        System.out.println("등록된 차량: " + carName);
        return 1;
    }
}

package com.study.mvc.repository;

import java.util.List;

public interface CarRepository {

    //interface는 기본이 추상메소드
    public List<String> getCarNames();
    public int insertCar(String carName);

}

package com.study.mvc.service;

import com.study.mvc.repository.CarRepository;

import java.util.List;

public interface CarService {

    public String getCarNames();
    public int add(String carName);
}

package com.study.mvc.service;

import com.study.mvc.repository.CarRepository;
import com.study.mvc.repository.CarRepositoryImpl;
import com.study.mvc.repository.CarRepositoryImpl2;
import com.study.mvc.util.TestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

// @Component 대신 @Service를 달아도 @Service안에 @Component 가 있어서 똑같다
@Service
public class CarServiceImpl implements CarService{

    //Impl 이름이 시리즈로 돼있을땐 @Component 이름을 잡아주고 @Qualifier에 해당 @component 명을 넣어주면 그것을 실행

    @Autowired
    @Qualifier("a")
    private CarRepository carRepository;

    // 단축키 Ctnl + i 하면 오버라이드
    @Override
    public String getCarNames() {
        return String.join(", ", carRepository.getCarNames());
        //결과 = 문자열: k3, k5
    }

    @Override
    public int add(String carName) {
        return 0;
    }
}

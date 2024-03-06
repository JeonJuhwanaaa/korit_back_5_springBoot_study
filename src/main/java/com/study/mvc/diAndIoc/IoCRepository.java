package com.study.mvc.diAndIoc;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class IoCRepository {
    private List<String> nameList = List.of("김도균", "정건희", "조성민");

    public Map<String, String> convertNameMap() {

        Map<String, String> nameMap = new HashMap<>();
        for(int i=0; i < nameList.size(); i++) {
            nameMap.put("name" + (i + 1), nameList.get(i));
        }
        return nameMap;
    }
}

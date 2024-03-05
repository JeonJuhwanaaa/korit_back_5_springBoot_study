package com.study.mvc.diAndIoc;

import java.util.List;

public class DiRepository {

    private List<Integer> scoreList = List.of(100, 90, 80, 70);

    public List<Integer> getScoreList() {
        return scoreList;
    }
}

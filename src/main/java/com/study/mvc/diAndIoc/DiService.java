package com.study.mvc.diAndIoc;

import java.util.List;

public class DiService {

    private DiRepository diRepository;

    // 생성자 : 외부에서
    public DiService(DiRepository diRepository) {
        this.diRepository = diRepository;
    }

    // getTotal 메소드에서 repository가 없으면 돌아가지 않기때문에 service가 repository를 의존한다고 말할수있다.
    public int getTotal() {

        int total = 0;

        List<Integer> scoreList = diRepository.getScoreList();
        for(Integer score : scoreList) {
            total += score;
        }
        return total;
    }

    public double getAverage() {

        double avg = 0;
        int total = 0;

        List<Integer> scoreList = diRepository.getScoreList();
        for(Integer score : scoreList) {
            total += score;
        }
        avg = total / scoreList.size();

        return avg;
    }
}

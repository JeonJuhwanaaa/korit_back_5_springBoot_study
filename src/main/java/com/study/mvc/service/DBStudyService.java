package com.study.mvc.service;

import com.study.mvc.dto.DBStudyReqDto;
import com.study.mvc.dto.DBStudyInsertRespDto;
import com.study.mvc.dto.DBStudySelectRespDto;
import com.study.mvc.entity.Study;
import com.study.mvc.repository.DBStudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DBStudyService {

    @Autowired
    private DBStudyRepository dbStudyRepository;

    public DBStudyInsertRespDto createStudy(DBStudyReqDto dbStudyReqDto) {

        // [AllArgs 식]
        // Study study = new Study(0, dbStudyReqDto.getName(), dbStudyReqDto.getAge(), null);
//        ------------------------------------------------------------------------------------
        // [NoArgs 식]
        // Study study = new Study();
        // study.setName(dbStudyReqDto.getName());
        // study.setAge(dbStudyReqDto.getAge());
//        ------------------------------------------------------------------------------------
        // [Builder 식]

        Study findStudy = dbStudyRepository.findStudyByName(dbStudyReqDto.getName());

        if(findStudy != null) {
            return DBStudyInsertRespDto.builder()
                    .successStatus(false)
                    .build();
        }

        Study study = Study.builder()
                .name(dbStudyReqDto.getName())
                .age(dbStudyReqDto.getAge())
                .build();

        int successCount = dbStudyRepository.save(study);

        DBStudyInsertRespDto dbStudyInsertRespDto = DBStudyInsertRespDto.builder()
                .id(study.getId())
                .name(study.getName())
                .age(study.getAge())
                .successStatus(successCount > 0)
                .successCout(successCount)
                .build();
        return dbStudyInsertRespDto;
    }

// 단일 검색
    public DBStudySelectRespDto findStudyById(int id) {

        Study study = dbStudyRepository.findStudyById(id);

        System.out.println(study);

        DBStudySelectRespDto dbStudySelectRespDto =
                DBStudySelectRespDto.builder()
                        .id(study.getId())
                        .name(study.getName())
                        .age(study.getAge())
                        .build();
        return dbStudySelectRespDto;
    }

    public DBStudySelectRespDto findStudyByName(String name) {
        Study study = dbStudyRepository.findStudyByName(name);
        return study == null ? null : study.toDto();
    }

    //    여러개 검색
    public List<DBStudySelectRespDto> findAll() {
        List<DBStudySelectRespDto> respDtoList = new ArrayList<>();

        List<Study> studyList = dbStudyRepository.findAll();

        // 하나씩 꺼내서 dto 형식으로 바꿔서 List에 넣어주기
        for(Study study : studyList) {
            respDtoList.add(study.toDto());
        }
        return respDtoList;
    }

    public List<DBStudySelectRespDto> findAll2() {

        return dbStudyRepository.findAll()
                .stream()
                .map(Study::toDto)
                .collect(Collectors.toList());
    }

    // 지우기
    public int deleteById(int id) {
        return dbStudyRepository.deleteById(id);
    }

    // 업데이트
    public int putById(int id, DBStudyReqDto dbStudyReqDto) {
        Study study = dbStudyReqDto.toEntity(id);
        return dbStudyRepository.putById(study);
    }

    public int patchById(int id, DBStudyReqDto dbStudyReqDto) {
        return dbStudyRepository.patchById(dbStudyReqDto.toEntity(id));
    }
}

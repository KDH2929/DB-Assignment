package com.title.Service;

import com.title.DAO.titleDAO;
import com.title.DTO.titleDTO;
import com.title.DTO.userDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class titleService {

    @Autowired
    titleDAO titleDAO;

    public List<titleDTO> selectMovies(String userId){
        List<titleDTO> titleDTOList = titleDAO.selectMovie(userId);
        return titleDTOList;
    }

    public userDTO selectUser(String userId){
        userDTO nowUser = titleDAO.selectUser(userId);
        return nowUser;
    }

    public List<titleDTO> selectOccupationMovies(String occupation){
        List<titleDTO> occupationMovies = titleDAO.selectOccupationMovies(occupation);
        return occupationMovies;
    }

    public List<titleDTO> selectAgeGroupMovies(int ageGroup){
        List<titleDTO> ageGroupMovies = titleDAO.selectAgeGroupMovies(ageGroup);
        return ageGroupMovies;
    }

    public List<titleDTO> selectGenderMovies(String gender) {
        List<titleDTO> genderMovies = titleDAO.selectGenderMovies(gender);
        return genderMovies;
    }

    public List<titleDTO> selectRecommendedMovies(String userId) {
        // 파이썬 스크립트 실행하는 코드
        List<titleDTO> recommendedMovies = new ArrayList<>();

        try {
            // 파이썬 스크립트 경로   // 아나콘다 환경에서 파이썬을 실행시켜야함.
            String scriptPath = "C:/Users/wlffj/Miniconda3/python.exe";

            // 파이썬 스크립트 실행 명령어
            String command = scriptPath + " C:/Users/wlffj/IdeaProjects/DBproject1/src/main/Python/recommend.py " + userId;

            // ProcessBuilder를 사용하여 파이썬 스크립트 실행
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
            builder.inheritIO();

            Process process = builder.start();

            // 프로세스가 종료될 때까지 대기. 여기서 자바가 부모프로세스, 파이썬이 자식프로세스이다.
            int exitCode = process.waitFor();

            if (exitCode != 0) {            // 에러 발생시의 예외처리부분들
                System.out.println("error occurred");
            }

        } catch (IOException | InterruptedException e) {
            System.out.println("error occurred");
            e.printStackTrace();
        }

        // DAO를 통해 DB에서 쿼리를 적용하여 원하는 데이터를 얻어옵니다.
        // 그리고 그 데이터는 titleController에 리턴되어 modelAndView객체에 추가되게됩니다.
        recommendedMovies = titleDAO.selectRecommendedMovies(userId);
        return recommendedMovies;

    }

    public Object selectRecommendedMovies_Cosine(String userId) {
        List<titleDTO> recommendedMovies_Cosine = titleDAO.selectRecommendedMovies_Cosine(userId);
        return recommendedMovies_Cosine;
    }
}

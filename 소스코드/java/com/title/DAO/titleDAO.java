package com.title.DAO;

import com.title.DTO.titleDTO;
import com.title.DTO.userDTO;
import java.util.List;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class titleDAO {

    @Autowired
    private SqlSessionTemplate sqlSession;

    private static final String namespace = "com.title";

    public List<titleDTO> selectMovie(String userId) {
        return sqlSession.selectList(namespace + ".selectMovie", userId);
    }

    public userDTO selectUser(String userId) {
        return sqlSession.selectOne(namespace + ".selectUser", userId);
    }

    public List<titleDTO> selectOccupationMovies(String occupation) {
        return sqlSession.selectList(namespace + ".selectOccupationMovies", occupation);
    }

    public List<titleDTO> selectAgeGroupMovies(int ageGroup) {
        return sqlSession.selectList(namespace + ".selectAgeGroupMovies", ageGroup);
    }

    public List<titleDTO> selectRecommendedMovies(String userId) {
        return sqlSession.selectList(namespace + ".selectRecommendedMovies", userId);
    }

    public List<titleDTO> selectRecommendedMovies_Cosine(String userId) {
        return sqlSession.selectList(namespace + ".selectRecommendedMovies_Cosine", userId);
    }

    public List<titleDTO> selectGenderMovies(String gender) {
        return sqlSession.selectList(namespace + ".selectGenderMovies", gender);
    }
}
package com.spring.mvc.chap04.repository;


import com.spring.mvc.chap04.entity.Score;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository("jdbc")
public class ScoreJdbcRepository implements ScoreRepository{

    private String url = "jdbc:mariadb://localhost:3306/spring";
    private String username = "root";
    private String password = "1234";
    public ScoreJdbcRepository() {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<Score> findAll() {
        return null;
    }

    @Override
    public List<Score> findAll(String sort) {
        List<Score> scoreList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, username, password)){

            String sql = "SELECT * FROM tbl_score";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                scoreList.add(new Score(rs));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
            return scoreList;
    }

    @Override
    public boolean save(Score score) {


        try (Connection conn = DriverManager.getConnection(url, username, password)){

            conn.setAutoCommit(false);

            String sql = "INSERT INTO tbl_score " +
                    " (name, kor, eng, math, total, avg, grade) " +
                    " VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);   //sql 실행 객체 pstmt

            pstmt.setString(1, score.getName());
            pstmt.setInt(2, score.getKor());
            pstmt.setInt(3, score.getEng());
            pstmt.setInt(4, score.getMath());
            pstmt.setInt(5, score.getTotal());
            pstmt.setDouble(6, score.getAverage());
            pstmt.setString(7, String.valueOf(score.getGrade()));

            int result = pstmt.executeUpdate(); // 성공시 1, 실패시 0
            if(result==1){
                conn.commit();
                return true;
            }
            conn.rollback();
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteByStuNum(int stuNum) {


        try  (Connection conn = DriverManager.getConnection(url, username, password)){

            conn.setAutoCommit(false);

            String sql ="DELETE FROM tbl_score " +
                    "WHERE stu_num=? ";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, stuNum);

            int result = pstmt.executeUpdate(); // 성공시 1, 실패시 0
            if(result==1){
                conn.commit();
                return true;
            }
            conn.rollback();
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Score findByStuNum(int stuNum) {

        try (Connection conn = DriverManager.getConnection(url, username, password)){

            String sql = "SELECT * FROM tbl_score WHERE stu_num=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, stuNum);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                return new Score(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

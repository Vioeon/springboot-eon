package net.likelion.bebc25.board03.post.repository;

import net.likelion.bebc25.board03.post.dto.PostDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PureJdbcPostRepository implements PostRepository{

    @Value("${spring.datasource.url}")
    private String URL;
    @Value("${spring.datasource.username}")
    private String USER;
    @Value("${spring.datasource.password}")
    private String PASSWORD;

    @Override
    public List<PostDto> findAll() {
        List<PostDto> result = new ArrayList<>();

        String sql = "SELECT id, author, title, content, secret, created_at FROM post2;";

        try(
                // DB 연결 ( Connection 객체 생성 )
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                // SQL 실행 객체 생성 ( Statement 객체 생성 )
                Statement stmt = conn.createStatement();
                // SQL 실행
                ResultSet rs = stmt.executeQuery(sql);
        ){
            // 결과 처리 ( ResultSet 사용 )
            while (rs.next()) {
                PostDto postDto = new PostDto();
                postDto.setId(rs.getInt("id"));
                postDto.setAuthor(rs.getString("author"));
                postDto.setTitle(rs.getString("title"));
                postDto.setSecret(rs.getBoolean("secret"));
                postDto.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));

                result.add(postDto);
            }
        }catch(SQLException e){
            System.out.println("에러 : " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public PostDto findById(int id) {
        PostDto postDto = null;

        String sql = "SELECT id, author, title, content, secret, created_at FROM post2 WHERE id = " + id + ";";

        try(
                // DB 연결 ( Connection 객체 생성 )
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                // SQL 실행 객체 생성 ( Statement 객체 생성 )
                Statement stmt = conn.createStatement();
                // SQL 실행
                ResultSet rs = stmt.executeQuery(sql);
        ){
            if (rs.next()) {
                postDto = new PostDto();
                postDto.setId(rs.getInt("id"));
                postDto.setAuthor(rs.getString("author"));
                postDto.setTitle(rs.getString("title"));
                postDto.setContent(rs.getString("content"));
                postDto.setSecret(rs.getBoolean("secret"));
                postDto.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
            }
        } catch (SQLException e) {
            System.out.println("에러 : " + e.getMessage());
        }
        return postDto;
    }

    @Override
    public void save(PostDto post) {
        String sql = "INSERT INTO post2(author, title, content) VALUES ('" + post.getAuthor() + "','" + post.getTitle() + "','" + post.getContent() + "');";

        try(
                // DB 연결 ( Connection 객체 생성 )
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                // SQL 실행 객체 생성 ( Statement 객체 생성 )
                Statement stmt = conn.createStatement();
        ){
            System.out.println(sql);
            int affectedRows = stmt.executeUpdate(sql);

            System.out.println("게시글 등록 " + affectedRows + "건 완료\n");
        } catch (SQLException e) {
            System.out.println("에러 : " + e.getMessage());
        }
    }

    @Override
    public void update(PostDto post) {
        String sql = "UPDATE post2 SET author = '" + post.getAuthor() + "', title = '" + post.getTitle() + "', content = '" + post.getContent() + "' WHERE id = " + post.getId() + ";";

        try(
                // DB 연결 ( Connection 객체 생성 )
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                // SQL 실행 객체 생성 ( Statement 객체 생성 )
                Statement stmt = conn.createStatement();
        ){
            int affectedRows = stmt.executeUpdate(sql);

            System.out.println(post.getId() + "번 게시글 수정 " + affectedRows + "건 완료\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM post2 WHERE id = " + id + ";";

        try(
                // DB 연결 ( Connection 객체 생성 )
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                // SQL 실행 객체 생성 ( Statement 객체 생성 )
                Statement stmt = conn.createStatement();
        ){
            int affectedRows = stmt.executeUpdate(sql);

            System.out.println(id + "번 게시글 삭제 " + affectedRows + "건 완료\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

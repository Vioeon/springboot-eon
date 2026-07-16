package net.likelion.bebc25.board03.post.repository;

import net.likelion.bebc25.board03.post.dto.PostDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcTemplatePostRepository implements PostRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplatePostRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<PostDto> postRowMapper = (ResultSet rs, int rowNum) -> {
//        return new PostDto(rs.getInt("id"),
//                rs.getString("title"),
//                rs.getString("content"),
//                rs.getString("author"),
//                rs.getBoolean("secret"),
//                rs.getObject("created_at", LocalDateTime.class));
        return PostDto.builder()
                .id(rs.getInt("id"))
                .author(rs.getString("author"))
                .title(rs.getString("title"))
                .content(rs.getString("content"))
                .secret(rs.getBoolean("secret"))
                .createdAt(rs.getObject("created_at", LocalDateTime.class)).build();
    };

    @Override
    public List<PostDto> findAll() {
        // jdbcTemplate.query는 여러번실행해서 List를 반환
        return jdbcTemplate.query("SELECT id, author, title, content, secret, created_at FROM post2", postRowMapper);
    }

    @Override
    public PostDto findById(int id) {
        // jdbcTemplate.queryForObject는 1번만 실행해서 객체를 반환
        return jdbcTemplate.queryForObject("SELECT id, author, title, content, secret, created_at FROM post2 WHERE id = ?", postRowMapper, id);
    }

    public void save(PostDto post) {
        jdbcTemplate.update("INSERT INTO post2(author, title, content) VALUE (?, ?, ?)"
                , post.getAuthor()
                , post.getTitle()
                , post.getContent());
    }

    @Override
    public void update(PostDto post) {
        jdbcTemplate.update("UPDATE post2 SET author = ?, title = ?, content = ? WHERE id = ?"
                , post.getAuthor()
                , post.getTitle()
                , post.getContent()
                , post.getId());
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update("DELETE FROM post2 WHERE id = ?", id);
    }
}

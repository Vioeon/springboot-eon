package net.likelion.bebc25.board03.post.repository;

import net.likelion.bebc25.board03.post.dto.PostDto;

import java.util.List;

public interface PostRepository {
    // DB 관점에서 작업할 것들
    List<PostDto> findAll();
    PostDto findById(int id);
    void save(PostDto post);
    void update(PostDto post);
    void deleteById(int id);
}

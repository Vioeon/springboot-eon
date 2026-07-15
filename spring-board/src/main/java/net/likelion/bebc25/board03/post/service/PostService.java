package net.likelion.bebc25.board03.post.service;

import net.likelion.bebc25.board03.post.dto.PostDto;

import java.util.List;

public interface PostService {
    // 사용자 관점에서 컨트롤러가 호출할 것들
    // DB 연동이외에 해야하는 것들
    List<PostDto> getPosts();
    PostDto getPost(int id);
    void writePost(PostDto post);
    void editPost(PostDto post);
    void removePost(int id);
}

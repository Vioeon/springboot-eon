package net.likelion.bebc25.board03.post.service;

import net.likelion.bebc25.board03.post.dto.PostDto;
import net.likelion.bebc25.board03.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    // 서비스 인터페이스 구현
    // 사용자 관점에서 컨트롤러가 호출할 것들
    // DB 연동이외에 해야하는 것들
    // 컨트롤러가 서비스를 호출하고 서비스가 레포지토리를 호출한다.

    // PostRepository를 의존해야한다.
    private final PostRepository postRepository;

    public PostServiceImpl(@Qualifier("jdbcTemplatePostRepository") PostRepository postRepository) {
        this.postRepository = postRepository; // 의존성 주입
    }

    @Override
    public List<PostDto> getPosts() {
        return postRepository.findAll();
    }

    @Override
    public PostDto getPost(int id) {
        return postRepository.findById(id);
    }

    @Override
    public void writePost(PostDto post) {
        postRepository.save(post);
    }

    @Override
    public void editPost(PostDto post) {
        postRepository.update(post);
    }

    @Override
    public void removePost(int id) {
        postRepository.deleteById(id);
    }
}

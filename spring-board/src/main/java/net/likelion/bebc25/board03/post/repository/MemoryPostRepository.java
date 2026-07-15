package net.likelion.bebc25.board03.post.repository;

import net.likelion.bebc25.board03.post.dto.PostDto;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MemoryPostRepository implements PostRepository{
    // DB 관점에서 작업할 것들

    private final List<PostDto> fakePosts;

    public MemoryPostRepository() {
        fakePosts = new ArrayList<PostDto>();
        PostDto post1 = new PostDto();
        post1.setId(1);
        post1.setTitle("1번 게시글");
        post1.setContent("1번 게시글 내용");
        post1.setAuthor("하루");
        post1.setSecret(true);
        post1.setCreatedAt(LocalDateTime.now());

        PostDto post2 = new PostDto();
        post2.setId(2);
        post2.setTitle("2번 게시글");
        post2.setContent("2번 게시글 내용");
        post2.setAuthor("나무");
        post2.setSecret(false);
        post2.setCreatedAt(LocalDateTime.now());

        fakePosts.add(post1);
        fakePosts.add(post2);
    }

    @Override
    public List<PostDto> findAll() {
        return fakePosts;
    }

    @Override
    public PostDto findById(int id) {
        for (PostDto p : findAll()) {
            if (p.getId() == id) {
                return p;
            }
        }
        throw new IllegalArgumentException(id + "번 게시글은 존재하지 않습니다.");
    }

    @Override
    public void save(PostDto post) {
        PostDto lastPost = findAll().getLast();
        post.setId(lastPost.getId() + 1);
        post.setCreatedAt(LocalDateTime.now());
        fakePosts.add(post);
    }

    @Override
    public void update(PostDto post) {
        PostDto targetPost = null;
        for (PostDto p : findAll()) {
            if (p.getId() == post.getId()) {
                targetPost = p;
                break;
            }
        }
        targetPost.setTitle(post.getTitle());
        targetPost.setContent(post.getContent());
        targetPost.setAuthor(post.getAuthor());
    }

    @Override
    public void deleteById(int id) {
        for (PostDto p : findAll()) {
            if (p.getId() == id) {
                fakePosts.remove(p);
                break;
            }
        }
    }
}

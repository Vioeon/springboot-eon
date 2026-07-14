package net.likelion.bebc25.board02.post.controller;

import lombok.extern.slf4j.Slf4j;
import net.likelion.bebc25.board02.post.dto.PostDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/02/board")
public class BoardController {
    private final List<PostDto> fakePosts;

    public BoardController() {
        fakePosts = new ArrayList<PostDto>();
        PostDto post1 = new PostDto();
        post1.setId(1);
        post1.setTitle("1번 게시글");
        post1.setContent("1번 게시글 내용");
        post1.setAuthor("하루");
        post1.setCreatedAt(LocalDateTime.now());

        PostDto post2 = new PostDto();
        post2.setId(2);
        post2.setTitle("2번 게시글");
        post2.setContent("2번 게시글 내용");
        post2.setAuthor("나무");
        post2.setCreatedAt(LocalDateTime.now());

        fakePosts.add(post1);
        fakePosts.add(post2);
    }

    public List<PostDto> getPosts() {
        List<PostDto> list = fakePosts;
        return list;
    }

    public PostDto getPost(int id) {
        PostDto post = null;
        for (PostDto p : fakePosts) {
            if (p.getId() == id) {
                post = p;
                break;
            }
        }
        return post;
    }

    // 게시글 목록 조회 컨트롤러
    @GetMapping("/list.html")
    public String getBoardList(Model model) {
        // 게시글 목록 조회(데이터)
        List<PostDto> posts = getPosts();
        model.addAttribute("posts", posts);
        return "board/list"; // url이 아닌 템플릿 경로라 .html 자동 붙여줌
    }

    @GetMapping("/detail.html")
    public String getDetail(@RequestParam int id, Model model) {
        PostDto post = getPost(id);
        model.addAttribute("post", post);
        log.debug("게시물 조회 : " + post.toString());
        return "board/detail"; // 템플릿 파일 경로
    }

    // 게시글 등록 화면 요청하는 컨트롤러
    @GetMapping("/write.html")
    public String getWriteForm(Model model) {
        model.addAttribute("postForm", new PostDto());
        return "board/write";
    }

    // 게시글 수정 화면 요청하는 컨트롤러
    @GetMapping("/edit.html")
    public String geteditForm(@RequestParam int id, Model model) {
        PostDto post = getPost(id);
        model.addAttribute("postForm", post);
        return "board/write";
    }

    // 게시글 등록 요청을 처리하는 컨트롤러
    @PostMapping("/write")
    public String writePost(@RequestParam("title") String title,
                            @RequestParam("content") String content,
                            @RequestParam("author") String author) {
        // 매개변수로 데이터 받아와서 db등록 작업
        PostDto post = new PostDto(title, content, author);
        // DB 저장 로직 호출
        savePost(post);

        // 브라우더에 list.html로 재요청하라고 응답
        return "redirect:list.html";
    }

    // 게시글 등록
    public void savePost(PostDto post) {
        PostDto lastPost = getPosts().getLast();
        post.setId(lastPost.getId() + 1);
        post.setCreatedAt(LocalDateTime.now());
        fakePosts.add(post);
    }

    // 게시글 수정 요청을 처리하는 컨트롤러
    @PostMapping("/edit")
    public String editPost(@ModelAttribute PostDto post) {
        log.debug("게시물 수정 : "  + post.toString());
        updatePost(post);
        return "redirect:detail.html?id=%d".formatted(post.getId());
    }

    // 게시글 수정한다
    public void updatePost(PostDto post) {
        PostDto targetPost = null;
        for (PostDto p : getPosts()) {
            if (p.getId() == post.getId()) {
                targetPost = p;
                break;
            }
        }
        targetPost.setTitle(post.getTitle());
        targetPost.setContent(post.getContent());
        targetPost.setAuthor(post.getAuthor());
    }

    // 게시글 삭제 요청을 처리하는 컨트롤러
    @PostMapping("/delete")
    public String deletePost(@RequestParam int id) {
        List<PostDto> posts = getPosts();
        for (PostDto p : posts) {
            if (p.getId() == id) {
                fakePosts.remove(p);
                break;
            }
        }
        // 삭제하고 그대로 게시글 목록 창
        return "redirect:list.html";
    }
}

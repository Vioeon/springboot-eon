package net.likelion.bebc25.board02.post.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import net.likelion.bebc25.board02.post.dto.PostDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    public List<PostDto> getPosts() {
        List<PostDto> list = fakePosts;
        return list;
    }

    public PostDto getPost(int id) {
        for (PostDto p : getPosts()) {
            if (p.getId() == id) {
                return p;
            }
        }
        throw new IllegalArgumentException(id + "번 게시글은 존재하지 않습니다.");
    }

    // 게시글 목록 조회 컨트롤러
    @GetMapping("/list.html")
    public String getBoardList(Model model) {
        // 게시글 목록 조회(데이터)
        List<PostDto> posts = getPosts();
        model.addAttribute("posts", posts); // html로 객체 보냄
        return "board/list"; // url이 아닌 템플릿 경로라 .html 자동 붙여줌
    }

    @GetMapping("/detail.html")
    public String getDetail(@RequestParam int id, Model model) {
        PostDto post = getPost(id);
        model.addAttribute("post", post); //post 객체를 Model에 담아서 View(HTML, Thymeleaf)에게 전달한다.
        log.debug("게시물 조회 : " + post.toString());
        return "board/detail"; // 템플릿 파일 경로
    }

    // 게시글 등록 화면 요청하는 컨트롤러
    @GetMapping("/write.html")
    public String getWriteForm(@ModelAttribute("postForm") PostDto post) { // View의 입력값(파라미터)을 받아 객체를 만든다
//        model.addAttribute("postForm", new PostDto()); // @ModelAttribute가 자동으로 수행해줌
        // @ModelAttribute는 "객체를 받아온다"와 "객체를 View로 전달한다" 두 가지 역할을 한다
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
    public String writePost(@Valid @ModelAttribute("postForm") PostDto post, // Validation 검증 대상 객체
                            BindingResult bindingResult) { // Validation 검증 결과 저장 객체(대상 객체 뒤에 기술해야함)
        if(bindingResult.hasErrors()){ // 검증 실패 시

            return "board/write"; // 작성중이던 페이지로 다시 이동
        }

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
    public String editPost(@Valid @ModelAttribute("postForm") PostDto post,
                           BindingResult bindingResult) {
        log.debug("게시물 수정 : "  + post.toString());
        if(bindingResult.hasErrors()){ // 검증 실패 시

            return "board/write"; // 작성중이던 페이지로 다시 이동
        }
        updatePost(post);
        return "redirect:detail.html?id=" + post.getId();
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
        removePost(id);

        return "redirect:list.html";
    }

    public void removePost(int id){
        List<PostDto> posts = getPosts();
        for (PostDto p : posts) {
            if (p.getId() == id) {
                fakePosts.remove(p);
                break;
            }
        }
    }
}

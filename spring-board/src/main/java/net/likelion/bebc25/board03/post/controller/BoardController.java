package net.likelion.bebc25.board03.post.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import net.likelion.bebc25.board03.post.dto.PostDto;
import net.likelion.bebc25.board03.post.service.PostService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/03/board")
public class BoardController {

    // 서비스에 의존해야 한다.
    private final PostService postService;

    public BoardController(PostService postService) {
        this.postService = postService; // 의존성 주입
    }

    // 게시글 목록 조회 컨트롤러
    @GetMapping("/list.html")
    public String getBoardList(Model model) {
        // 게시글 목록 조회(데이터)
        List<PostDto> posts = postService.getPosts();
        model.addAttribute("posts", posts);
        return "board/list"; // url이 아닌 템플릿 경로라 .html 자동 붙여줌
    }

    @GetMapping("/detail.html")
    public String getDetail(@RequestParam int id, Model model) {
        PostDto post = postService.getPost(id);
        model.addAttribute("post", post); //post 객체를 Model에 담아서 View(HTML, Thymeleaf)에게 전달한다.
        return "board/detail"; // 템플릿 파일 경로
    }

    // 게시글 등록 화면 요청하는 컨트롤러
    @GetMapping("/write.html")
    public String getWriteForm(@ModelAttribute("postForm") PostDto post) {
        return "board/write";
    }

    // 게시글 수정 화면 요청하는 컨트롤러
    @GetMapping("/edit.html")
    public String geteditForm(@RequestParam int id, Model model) {
        PostDto post = postService.getPost(id);
        model.addAttribute("postForm", post);
        return "board/write";
    }

    // 게시글 등록 요청을 처리하는 컨트롤러
    @PostMapping("/write")
    public String writePost(@Valid @ModelAttribute("postForm") PostDto post, // Validation 검증 대상 객체
                            BindingResult bindingResult) { // Validation 검증 결과 저장 객체(대상 객체 뒤에 기술해야함)
        if(bindingResult.hasErrors()){ // 검증 실패 시
            return "board/write";
        }
        postService.writePost(post);
        return "redirect:list.html";
    }

    // 게시글 수정 요청을 처리하는 컨트롤러
    @PostMapping("/edit")
    public String editPost(@Valid @ModelAttribute("postForm") PostDto post,
                           BindingResult bindingResult) {
        if(bindingResult.hasErrors()){ // 검증 실패 시
            return "board/write";
        }
        postService.editPost(post);
        return "redirect:detail.html?id=" + post.getId();
    }

    // 게시글 삭제 요청을 처리하는 컨트롤러
    @PostMapping("/delete")
    public String deletePost(@RequestParam int id) {
        postService.removePost(id);
        return "redirect:list.html";
    }
}

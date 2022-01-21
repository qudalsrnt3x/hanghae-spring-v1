package com.hanghae.hanghae_spring_prac.controller;

import com.hanghae.hanghae_spring_prac.domain.Posts;
import com.hanghae.hanghae_spring_prac.domain.PostsRepository;
import com.hanghae.hanghae_spring_prac.domain.PostsRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostsApiController {

    private final PostsRepository postsRepository;

    @GetMapping("/posts")
    public List<Posts> getPosts(){
        return postsRepository.findAllByOrderByCreatedAtDesc();
    }

    @PostMapping("/posts")
    public Long savePosts(@RequestBody PostsRequestDto postsRequestDto) {
        Posts posts = new Posts(postsRequestDto);

        return postsRepository.save(posts).getId();
    }

    @GetMapping("/posts/{id}")
    public Posts getPost(@PathVariable Long id) {
        return postsRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
    }
}

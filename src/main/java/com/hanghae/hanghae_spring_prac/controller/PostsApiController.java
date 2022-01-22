package com.hanghae.hanghae_spring_prac.controller;

import com.hanghae.hanghae_spring_prac.domain.Posts;
import com.hanghae.hanghae_spring_prac.domain.PostsRepository;
import com.hanghae.hanghae_spring_prac.domain.PostsRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    public ResponseEntity<Posts> savePosts(@RequestBody PostsRequestDto postsRequestDto) {
        Posts posts = new Posts(postsRequestDto);

        Posts savedPosts = postsRepository.save(posts);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPosts.getId())
                .toUri();

        // response header에 Location: http://localhost:8080/posts/1  으로 확인 가능

        return ResponseEntity.created(location).build();

    }

    @GetMapping("/posts/{id}")
    public Posts getPost(@PathVariable Long id) {
        return postsRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
    }
}

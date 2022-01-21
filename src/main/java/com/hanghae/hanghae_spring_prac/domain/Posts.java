package com.hanghae.hanghae_spring_prac.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Posts extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String contents;

    public Posts(PostsRequestDto postsRequestDto) {
        this.title = postsRequestDto.getTitle();
        this.author = postsRequestDto.getAuthor();
        this.contents = postsRequestDto.getContents();
    }
}

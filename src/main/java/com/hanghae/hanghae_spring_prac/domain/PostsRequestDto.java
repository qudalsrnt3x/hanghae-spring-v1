package com.hanghae.hanghae_spring_prac.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostsRequestDto {

    private String title;
    private String author;
    private String contents;
}

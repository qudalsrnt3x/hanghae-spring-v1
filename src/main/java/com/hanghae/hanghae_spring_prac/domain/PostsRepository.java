package com.hanghae.hanghae_spring_prac.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {

    List<Posts> findAllByOrderByCreatedAtDesc();

}

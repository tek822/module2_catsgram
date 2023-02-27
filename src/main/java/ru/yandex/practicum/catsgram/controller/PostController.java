package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PostController {

    private final PostService postService;
    private static final Logger log = LoggerFactory.getLogger(PostController.class);

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public List<Post> findAll(
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "desc") String sort
    ){
        if (!("desc".equals(sort) || "asc".equals(sort))) {
            throw new IllegalArgumentException("Неизвестный порядок сортировки: " + sort);
        }
        if (page < 0) {
            throw new IllegalArgumentException("Ошибка в параметре from: " + page);
        }
        if (size <= 0) {
            throw new IllegalArgumentException("Ошибка в параметре size: " + size);
        }

        List<Post> posts = postService.findAll(size, page * size, sort);
        log.debug("Выбрано posts: {}", posts.size());
        return posts;
    }

    @GetMapping("/post/{id}")
    public Post findPostById (@PathVariable("id") int id) {
        return postService.findById(id);
    }

    @PostMapping(value = "/post")
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }
}
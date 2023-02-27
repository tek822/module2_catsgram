package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.exception.PostNotFoundException;
import ru.yandex.practicum.catsgram.exception.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final List<Post> posts = new ArrayList<>();
    private static int nextId = 0;
    private final UserService userService;

    public PostService(UserService userService) {
        this.userService = userService;
    }

    public List<Post> findAll(int size, int from, String sort) {
        return posts.stream().sorted( (p0,p1) -> {
            int comp = p0.getCreationDate().compareTo(p1.getCreationDate());
            if ("desc".equals(sort)) {
                comp *= -1;
            }
            return comp;
        }).skip(from).limit(size).collect(Collectors.toList());
    }

    public List<Post> findAll() {
        return posts;
    }

    public Post create(Post post) {
        String email = post.getAuthor();
        if (email == null || email.isBlank()) {
            throw new InvalidEmailException("Не задан email пользователя");
        }
        if (userService.findUserByEmail(email) == null){
            throw new UserNotFoundException("Пользователь " + email + " не найден");
        }
        post.setId(nextId++);
        posts.add(post);
        return post;
    }

    public Post findById(Integer id) {
        Optional<Post> found  = posts.stream()
                .filter(p->p.getId().equals(id))
                .findFirst();
        if (found.isPresent()) {
            return found.get();
        } else {
            throw new PostNotFoundException(String.format("Пост № %d не найден", id));
        }
    }

    public List<Post> findByEmail(String friend, int size, String sort) {
        return posts.stream()
                .filter((p) -> friend.equals(p.getAuthor()))
                .sorted( (p0,p1) -> {
                    int comp = p0.getCreationDate().compareTo(p1.getCreationDate());
                    if ("desc".equals(sort)) {
                        comp *= -1;
                    }
                    return comp;
                })
                .limit(size).collect(Collectors.toList());
    }
}

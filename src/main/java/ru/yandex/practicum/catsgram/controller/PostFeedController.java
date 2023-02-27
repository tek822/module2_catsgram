package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PostFeedController {
    private final PostService postService;
    private final ObjectMapper objectMapper;
    private static final Logger log = LoggerFactory.getLogger(PostController.class);

    @Autowired
    public PostFeedController(PostService postService, ObjectMapper objectMapper) {
        this.postService = postService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/feed/friends")
    public List<Post> getFriendsFeed (@RequestBody String requestBody) {
        //"{\"sort\":\"desc\",\"size\":3,\"friends\":[\"puss@boots.com\",\"cat@dogs.net\",\"purrr@luv.me\"]}"
        List<String> friends ;
        String sort;
        int size;
        List<Post> posts = null;
        try {
            String stringParams = objectMapper.readValue(requestBody, String.class);
            JsonNode jsonNode = objectMapper.readTree(stringParams);
            sort = jsonNode.get("sort").asText();
            size = jsonNode.get("size").asInt();
            String friendsString = jsonNode.get("friends").asText();
            friends = objectMapper.readValue(friendsString, new TypeReference<List<String>>(){});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Неправильный формат json" , e);
        }

        if (friends != null) {
            for (String friend : friends) {
                posts.addAll(postService.findByEmail(friend, size, sort));
            }
        }
        return posts;
    }

}

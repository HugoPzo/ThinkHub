package mx.unam.aragon.thinkhub.controllers;


import mx.unam.aragon.thinkhub.entities.Post;
import mx.unam.aragon.thinkhub.services.PostService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping
public class MainController {

    private final PostService postService;

    public MainController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public String index() {
        return "index";
    }

    // ALL POSTS --------------------------------------------------------
    @GetMapping("/posts")
    public String posts(Model model) {
        model.addAttribute("posts", postService.getPosts());
        return "posts";
    }

    // POST - GET
    @GetMapping("/new_post")
    public String newUser(Model model) {
        model.addAttribute("post", new Post());
        return "new_post";
    }

    // POST - POST
    @PostMapping("/save_post")
    public String savePost(
         @ModelAttribute Post post
    ) {
        // Bitacora
        LoggerFactory.getLogger(getClass()).info("Post saved");
        // DB sending
        postService.savePost(post);
        return "redirect:/posts";
    }

    // POST - GET ID
    @GetMapping("/post/{id}")
    public String showPost(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.getPostById(id));
        return "post";
    }

    // POST DELETE
    @PostMapping("/delete_post/{id}")
    public String deletePost(@PathVariable Long id) {
        postService.deletePostById(id);
        return "redirect:/posts";
    }

    // POST - EDIT GET
    @GetMapping("/edit_post/{id}")
    public String editPost(@PathVariable Long id, Model model) {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "edit_post";
    }

    // POST - UPDATE POST
    @PostMapping("/update_post")
    public String updatePost(@ModelAttribute Post post) {
        Post existingPost = postService.getPostById(post.getId());
        if (existingPost != null) {
            existingPost.setText(post.getText());
            existingPost.setImage(post.getImage());
            existingPost.setUpdatedAt(LocalDateTime.now());
            postService.savePost(existingPost);
        }
        return "redirect:/posts";
    }

    // -------------------------------------------------------------


}

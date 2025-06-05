package mx.unam.aragon.thinkhub.controllers;


import java.time.LocalDateTime;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import mx.unam.aragon.thinkhub.entities.Comment;
import mx.unam.aragon.thinkhub.entities.Post;
import mx.unam.aragon.thinkhub.services.CommentService;
import mx.unam.aragon.thinkhub.services.PostService;
@Controller
@RequestMapping
public class MainController {

   
   private final PostService postService;
private final CommentService commentService; // <-- Asegúrate de agregar esto

public MainController(PostService postService, CommentService commentService) {
    this.postService = postService;
    this.commentService = commentService; // <-- Inicialización aquí
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
  @GetMapping("/post/{id}/comentarios")
public String comentar(@PathVariable Long id, Model model) {
    Post post = postService.getPostById(id);
    model.addAttribute("post", post);
    model.addAttribute("comment", new Comment()); // <-- Esto es clave
    return "comentarios";
}


@PostMapping("/post/{id}/comentarios")
public String guardarComentario(@PathVariable Long id, @ModelAttribute Comment comment) {
    Post post = postService.getPostById(id);
    comment.setPost(post);
    comment.setCreatedAt(LocalDateTime.now()); // Asegura fecha creación
    commentService.save(comment);
    return "redirect:/post/" + id + "/comentarios";
}



}

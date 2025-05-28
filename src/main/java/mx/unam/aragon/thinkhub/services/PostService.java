package mx.unam.aragon.thinkhub.services;

import mx.unam.aragon.thinkhub.entities.Post;
import mx.unam.aragon.thinkhub.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public boolean savePost(Post post) {
        if (post.getId() != null) {
            System.out.println("Actualizando post con ID: " + post.getId());
        } else {
            System.out.println("Creando nuevo post");
        }
        Post savedPost = postRepository.save(post);
        return savedPost != null;
    }

    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post no encontrado"));
    }

    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

}

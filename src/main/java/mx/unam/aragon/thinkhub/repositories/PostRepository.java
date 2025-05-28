package mx.unam.aragon.thinkhub.repositories;

import mx.unam.aragon.thinkhub.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

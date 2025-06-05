package mx.unam.aragon.thinkhub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.unam.aragon.thinkhub.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

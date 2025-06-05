package mx.unam.aragon.thinkhub.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.unam.aragon.thinkhub.entities.Comment;
import mx.unam.aragon.thinkhub.repositories.CommentRepository;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public void save(Comment comment) {
        commentRepository.save(comment);
    }
}

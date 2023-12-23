package plus.plusassignment.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import plus.plusassignment.domain.comment.dao.CommentWithPostAndUser;
import plus.plusassignment.domain.comment.entity.Comment;
import plus.plusassignment.domain.comment.repository.CommentRepository;
import plus.plusassignment.domain.comment.repository.custom.CustomCommentRepository;
import plus.plusassignment.global.exception.comment.CommentNotFoundException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final CustomCommentRepository customCommentRepository;

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
    }

    public CommentWithPostAndUser findCommentByIdWithPostAndUser(Long commentId) {

        return customCommentRepository.findByIdWithPostAndUser(
                commentId).orElseThrow(CommentNotFoundException::new);
    }

    public Page<CommentWithPostAndUser> findAllByIdWithPostAndUser(Long postId, Pageable pageable) {

        return customCommentRepository.findAllByIdWithPostAndUser(postId, pageable);
    }
}

package com.example.IS.service;

import com.example.IS.controller.form.CommentForm;
import com.example.IS.controller.form.MessageForm;
import com.example.IS.dto.UserComment;
import com.example.IS.repository.CommentRepository;
import com.example.IS.repository.entity.Comment;
import com.example.IS.repository.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    /*
     * レコード全件取得処理
     */
    public List<UserComment> findAllCommentWithUser() {
        final int LIMIT_NUM = 1000;
        List<Object[]> results = commentRepository.findAllWithUserByCreatedDateAsc(LIMIT_NUM);
        return setUserComment(results);
    }

    /*
     * DBから取得したデータをDtoに設定
     */
    private List<UserComment> setUserComment(List<Object[]> results) {
        List<UserComment> comments = new ArrayList<>();

        for (Object[] objects : results) {
            UserComment comment = new UserComment();
            comment.setId((Integer) objects[0]);
            comment.setText((String) objects[1]);
            comment.setUserId((Integer) objects[2]);
            comment.setMessageId((Integer) objects[3]);
            comment.setAccount((String) objects[4]);
            comment.setName((String) objects[5]);
            comment.setBranchId((Integer) objects[6]);
            comment.setDepartmentId((Integer) objects[7]);
            comment.setCreatedDate((LocalDateTime) objects[8]);
            comment.setUpdatedDate((LocalDateTime) objects[9]);
            comments.add(comment);
        }
        return comments;
    }

    /*
     * 返信登録処理（鈴木）
     */
    public void addComment(CommentForm commentForm){
        //型をForm→Entityに変換する用メソッド
        Comment comment = setComment(commentForm);

        //登録処理
        commentRepository.save(comment);
    }
    private Comment setComment(CommentForm commentForm) {
        Comment comment = new Comment();
        comment.setId(commentForm.getId());
        comment.setText(commentForm.getText());
        comment.setMessageId(commentForm.getMessageId());
        comment.setUserId(commentForm.getUserId());
        comment.setCreatedDate(commentForm.getCreatedDate());
        comment.setUpdatedDate(commentForm.getUpdatedDate());
        return comment;
    }
    /*
     * 返信削除処理（鈴木）
     */
    public void deleteComment(Integer id) {
        commentRepository.deleteById(id);
    }
}

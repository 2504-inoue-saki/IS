package com.example.IS.repository;

import com.example.IS.repository.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query("SELECT c.id, c.text, c.userId, c.messageId, u.account, u.name, u.branchId, u.departmentId, c.createdDate," +
            " c.updatedDate FROM Comment c INNER JOIN User u ON c.userId = u.id ORDER BY c.createdDate ASC LIMIT :num")
    List<Object[]> findAllWithUserByCreatedDateAsc(
            @Param("num") int num
    );
}

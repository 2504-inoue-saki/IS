package com.example.IS.repository;

import com.example.IS.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /*
     * ユーザ管理画面表示処理
     */
    @Query("SELECT " +
            "u.id as id, " +
            "u.account as account, " +
            "u.name as name, " +
            "u.branchId as branchId, " +
            "u.departmentId as departmentId, " +
            "u.isStopped as isStopped, " +
            "b.name as branchName, " +
            "d.name as departmentName " +
            "FROM User u " +
            "INNER JOIN Branch b ON u.branchId = b.id " +
            "INNER JOIN Department d ON u.departmentId = d.id " +
            "ORDER BY id ASC ")
    public List<Object[]> findAllUser();

    /*
     * アカウント重複チェック
     */
    public boolean existsByAccount(String account);

    /*
     * ログイン処理＆IDが同じならアカウント名が同じでもOKのやつ
     */
    public User findByAccount(String account);

    /*
     * ユーザ復活・停止処理
     */
    @Modifying
    @Query("UPDATE User u SET u.isStopped = :isStopped, u.updatedDate = CURRENT_TIMESTAMP WHERE u.id = :id")
    public void saveIsStopped(@Param("id") Integer id, @Param("isStopped") int isStopped);
}

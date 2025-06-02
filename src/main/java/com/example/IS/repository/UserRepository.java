package com.example.IS.repository;

import com.example.IS.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /*
     * ユーザ管理画面表示処理
     */
    // ユーザを全件取得
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
     * ログイン処理
     */
    public List<User> findByAccountAndPassword(String account, String password);
}

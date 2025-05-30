package com.example.IS.service;

import com.example.IS.controller.form.UserForm;
import com.example.IS.repository.UserRepository;
import com.example.IS.repository.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    /*
     * ユーザ管理画面表示処理
     */
    public List<UserForm> findUserWithBranchWithDepartment() {
        List<Object[]> results = userRepository.findAllUser();
        //List<Object[]>をList<UserForm>に詰め替えるメソッド呼び出し
        List<UserForm> formReturns = setListUserForm(results);
        return formReturns;
    }
    //List<Object[]>をList<UserForm>に詰め替えるメソッド
    private List<UserForm> setListUserForm(List<Object[]> results) {
        List<UserForm> formUsers = new ArrayList<>();
        for (Object[] objects : results) {
            UserForm formUser = new UserForm();
            formUser.setId((int) objects[0]);
            formUser.setAccount((String) objects[1]);
            formUser.setName((String) objects[2]);
            formUser.setBranchId((int) objects[3]);
            formUser.setDepartmentId((int) objects[4]);
            formUser.setIsStopped(((Integer) objects[5]).shortValue());
            formUser.setBranchName((String) objects[6]);
            formUser.setDepartmentName((String) objects[7]);
            formUsers.add(formUser);
        }
        return formUsers;
    }

    /*
     * ユーザー登録処理
     */
    public void addUser(UserForm userForm) {
//        ☆検討//パスワードを暗号化
//        String encPassword = passwordEncoder.encode(userForm.getPassword());
//        //暗号化したパスワードでセットし直す
//        userForm.setPassword(encPassword);
        //引数の型をForm→Entityに変換するメソッド呼び出し
        User user = setUserEntity(userForm);
        //ユーザー情報を登録
        userRepository.save(user);
    }
    //型をForm→Entityに変換するメソッド
    private User setUserEntity(UserForm reqUser) {
        User user = new User();
        user.setId(reqUser.getId());
        user.setAccount(reqUser.getAccount());
        user.setPassword(reqUser.getPassword());
        user.setName(reqUser.getName());
        user.setBranchId(reqUser.getBranchId());
        user.setDepartmentId(reqUser.getDepartmentId());
        //登録時は初期値で0(稼働)になる
        user.setIsStopped(reqUser.getIsStopped());
        user.setCreatedDate(reqUser.getCreatedDate());
        user.setUpdatedDate(reqUser.getUpdatedDate());
        return user;
    }
    /*
     * ログイン処理
     */
    public UserForm findLoginUser(UserForm userForm) {
        //パスワードの暗号化
        String encPassword = passwordEncoder.encode(userForm.getPassword());
        //暗号化したパスワードでセットし直す
        userForm.setPassword(encPassword);
        //DBへのselect処理
        List<User> users = userRepository.findByAccountAndPassword(userForm.getAccount(), userForm.getPassword());

        //引数の型をEntity→Formに変換するメソッド呼び出し
        List<UserForm> userForms = setUserForm(users);
        return userForms.get(0);
    }
    //型をEntity→Formに変換するメソッド
    private List<UserForm> setUserForm(List<User> users) {
        List<UserForm> userForms = new ArrayList<>();
        for (User value : users) {
            UserForm userForm = new UserForm();
            userForm.setId(value.getId());
            userForm.setAccount(value.getAccount());
            userForm.setPassword(value.getPassword());
            userForm.setName(value.getName());
            userForm.setBranchId(value.getBranchId());
            userForm.setDepartmentId(value.getDepartmentId());
            userForm.setIsStopped(value.getIsStopped());
            userForm.setCreatedDate(value.getCreatedDate());
            userForm.setUpdatedDate(value.getUpdatedDate());
            userForms.add(userForm);
        }
        return userForms;
    }

    /*
     * ユーザ編集画面表示処理
     */
    public ??? findEditUser(String id){

    }


}

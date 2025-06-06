package com.example.IS.service;

import com.example.IS.controller.form.UserForm;
import com.example.IS.repository.UserRepository;
import com.example.IS.repository.entity.User;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /*
     * ユーザ管理画面表示処理
     */
    public List<UserForm> findUserDate() {
        List<Object[]> results = userRepository.findAllUser();
        //List<Object[]>をList<UserForm>に詰め替えるメソッド呼び出し
        return setListUserForm(results);
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
     * ユーザー登録＆編集処理
     */
    public void saveUser(UserForm userForm) {
        //パスワードの入力がある場合は暗号化してセットし直す
        if(!StringUtils.isBlank(userForm.getPassword())){
            //パスワードを暗号化
            String encPassword = passwordEncoder.encode(userForm.getPassword());
            //暗号化したパスワードでセットし直す
            userForm.setPassword(encPassword);
        } else {
            //パスワードの入力ない場合は今のパスワードをDBから取得してセットする
            //DBへのselect処理
            User currentUser = userRepository.findById(userForm.getId()).orElse(null);
            //取得したパスワードをセット
            userForm.setPassword(currentUser.getPassword());
        }
        //引数の型をForm→Entityに変換するメソッド呼び出し
        User user = setUserEntity(userForm);
        //ユーザー情報を登録/更新
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
        user.setIsStopped(reqUser.getIsStopped());
        user.setCreatedDate(reqUser.getCreatedDate());
        user.setUpdatedDate(reqUser.getUpdatedDate());
        return user;
    }
    //IDが同じならアカウント名が同じでもOKのやつ
    public int findId(String account) {
        //DBへのselect処理
        User selectedUser = userRepository.findByAccount(account);
        return selectedUser.getId();
    }

    /*
     * アカウント重複チェック
     */
    public boolean existCheck(String account){
        if (userRepository.existsByAccount(account)){
            return true;
        } else {
            return false;
        }
    }

    /*
     * ログイン処理
     */
    public UserForm findLoginUser(UserForm userForm) {
        //DBへのselect処理
        User selectedUser = userRepository.findByAccount(userForm.getAccount());
        //リクエストから取得したパスワード(平文)とDBから取得したパスワード(暗号化済み)を比較し、異なった場合はnullを返す
        if(selectedUser == null || !passwordEncoder.matches(userForm.getPassword(),selectedUser.getPassword())){
            return null;
        }
        //setUserFormメソッドを使いたいがためにリストに入れる
        List<User> users = new ArrayList<>();
        users.add(selectedUser);
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
            userForms.add(userForm);
        }
        return userForms;
    }

    /*
     * ユーザ編集画面表示処理
     */
    public UserForm findEditUser(Integer id){
        //DBへのselect処理
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            return null;
        }
        //setUserFormメソッドを使いたいがためにリストに入れる
        List<User> users = new ArrayList<>();
        users.add(user);
        //引数の型をEntity→Formに変換するメソッド呼び出し
        List<UserForm> userForms = setUserForm(users);
        return userForms.get(0);
    }

    /*
     * ユーザ復活・停止処理
     */
    public void saveIsStopped(UserForm user){
        userRepository.saveIsStopped(user.getId(), user.getIsStopped());
    }
}

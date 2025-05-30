package com.example.IS.service;

import com.example.IS.controller.form.UserForm;
import com.example.IS.repository.UserRepository;
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
}

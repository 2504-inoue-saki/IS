package com.example.IS.controller;

import com.example.IS.controller.form.UserForm;
import com.example.IS.service.BranchService;
import com.example.IS.service.DepartmentService;
import com.example.IS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public class UserManageController {
    @Autowired
    UserService userService;

    @Autowired
    BranchService branchService;

    @Autowired
    DepartmentService departmentService;

    /*
     * ユーザ管理画面表示処理
     */
    @GetMapping("/userAdmin")
    public ModelAndView adminView() { // ここを元に戻す
        ModelAndView mav = new ModelAndView();
        // 登録してあるユーザー情報を取得
        List<UserForm> userData = userService.findUserWithBranchWithDepartment();
        mav.addObject("users", userData);
        //画面遷移先指定
        mav.setViewName("userAdmin");
        //フォワード
        return mav;
    }
}

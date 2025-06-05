package com.example.IS.controller;

import com.example.IS.controller.form.UserForm;
import com.example.IS.service.BranchService;
import com.example.IS.service.DepartmentService;
import com.example.IS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
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
    public ModelAndView adminView() {
        ModelAndView mav = new ModelAndView();
        // 登録してあるユーザー情報を取得
        List<UserForm> userData = userService.findUserDate();
        mav.addObject("users", userData);
        //画面遷移先指定
        mav.setViewName("/userAdmin");
        //フォワード
        return mav;
    }

    /*
     * ユーザ復活・停止処理
     */
    @PutMapping("/switch/{id}")
    public ModelAndView switchStatus(@PathVariable Integer id,
                                     @RequestParam(name = "isStopped", required = false) Integer isStopped) {
        //取得したリクエストをuserにセットする
        UserForm user = new UserForm();
        user.setId(id);
        user.setIsStopped(isStopped);
        //ユーザー復活停止状態の更新
        userService.saveIsStopped(user);
        //ユーザー管理画面へリダイレクト
        return new ModelAndView("redirect:/userAdmin");
    }
}

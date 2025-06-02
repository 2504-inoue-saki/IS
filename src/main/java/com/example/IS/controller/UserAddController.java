package com.example.IS.controller;

import com.example.IS.controller.form.UserForm;
import com.example.IS.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.IS.constFolder.ErrorMessage.*;

@Controller
public class UserAddController {
    //インスタンスの注入
    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;

    /*
     * ユーザー登録画面表示
     */
    @GetMapping("/signup")
    public ModelAndView userAddContent() {
        ModelAndView mav = new ModelAndView();
        //空のインスタンスを用意しておく
        UserForm addUser = new UserForm();
        mav.addObject("addUser", addUser);
        mav.setViewName("/userAdd");
        return mav;
    }

    /*
     * ユーザー登録処理
     */
    @PostMapping("/userAdd")
    //リクエストパラメータの取得
    public ModelAndView userAddProcessContent(@ModelAttribute("addUser") UserForm addUser) {

        //バリデーション

        //今の時間をセット
        LocalDateTime now = LocalDateTime.now();
        addUser.setCreatedDate(now);
        addUser.setUpdatedDate(addUser.getCreatedDate());

        //入力された情報を登録しに行く
        userService.saveUser(addUser);
        //ユーザー管理画面へリダイレクト
        return new ModelAndView("redirect:/userAdmin");
    }

}


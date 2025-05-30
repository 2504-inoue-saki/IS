package com.example.IS.controller;

import com.example.IS.controller.form.UserForm;
import com.example.IS.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    //インスタンスの注入
    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;

    /*
     * ログイン画面表示
     */
    @GetMapping("/login")
    public ModelAndView loginContent() {
        ModelAndView mav = new ModelAndView();
        UserForm loginUser = new UserForm();
        mav.addObject("loginUser", loginUser);
        mav.setViewName("/login");
        return mav;
    }

    /*
     * ログイン処理
     */
    @PostMapping("/loginProcess")
    //リクエストパラメータの取得
    public ModelAndView loginContent(@ModelAttribute("loginUser") UserForm requestLogin) {
        ModelAndView mav = new ModelAndView();

        //バリデーション

        // 入力されたアカウントとパスワードが存在するか確認しに行く
        UserForm loginUser = userService.findLoginUser(requestLogin);

//        //ログインユーザー情報チェック
//        if (loginUser == null || loginUser.getIsStopped() == 1) {
//            mav.addObject("errorMessage", E0003);
//            // 画面遷移先を指定
//            mav.setViewName("/login");
//            return mav;
//        }

        //無事にアカウントがあった場合はログイン情報を保持＆ホーム画面へリダイレクト
        session.setAttribute("loginUser", loginUser);
        return new ModelAndView("redirect:/");
    }

}

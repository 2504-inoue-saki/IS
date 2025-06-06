package com.example.IS.controller;

import com.example.IS.controller.form.UserForm;
import com.example.IS.service.BranchService;
import com.example.IS.service.DepartmentService;
import com.example.IS.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserManageController {
    @Autowired
    UserService userService;

    @Autowired
    BranchService branchService;

    @Autowired
    DepartmentService departmentService;
    @Autowired
    HttpServletRequest request;

    /*
     * ユーザ管理画面表示処理
     */
    @GetMapping("/userAdmin")
    public ModelAndView adminView() {
        ModelAndView mav = new ModelAndView();
        //セッションの獲得
        HttpSession session = request.getSession(true);
        //存在しないidの編集を行う際のエラーメッセージ表示処理
        if (session.getAttribute("errorMessages") != null) {
            //フィルターメッセージをエラーメッセージ用リストに入れる（List<String>に合わせる）
            List<String> errorMessages = (List<String>) session.getAttribute("errorMessages");
            session.removeAttribute("errorMessages");
            //エラーメッセージが詰まったリストをviewに送る
            mav.addObject("errorMessages", errorMessages);
        }
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

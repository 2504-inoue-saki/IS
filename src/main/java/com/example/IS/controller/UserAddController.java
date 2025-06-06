package com.example.IS.controller;

import com.example.IS.controller.form.UserForm;
import com.example.IS.groups.AddGroup;
import com.example.IS.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
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
    public ModelAndView userAddProcessContent(@Validated({AddGroup.class}) @ModelAttribute("addUser") UserForm addUser, BindingResult result) {

        ModelAndView mav = new ModelAndView();
        //リクエストパラメータの必須＆文字数＆半角文字チェック
        if (result.hasErrors()) {
            //エラーメッセージを入れる用のリストを作っておく
            List<String> errorMessages = new ArrayList<String>();
            //result.getFieldErrors()はresultの持つ全エラーを要素にしたリスト→型はList<FieldError>
            //要素を1つ取り出してerrorに代入して処理→全ての要素が尽きるまで繰り返す
            for (FieldError error : result.getFieldErrors()) {
                //error.getDefaultMessage()で取得したエラーメッセージをリストに追加
                errorMessages.add(error.getDefaultMessage());
            }
            //エラーメッセージが詰まったリストをviewに送る
            mav.addObject("errorMessages", errorMessages);
            // 画面遷移先を指定
            mav.setViewName("/userAdd");
            return mav;
        }

        //妥当性チェック①パスワードと確認用パスワードが異なる時にエラーメッセージ
        if (!addUser.getPassword().equals(addUser.getCheckPassword())) {
            //エラーメッセージを入れる用のリストを作っておく
            List<String> errorMessages = new ArrayList<String>();
            errorMessages.add(E0018);
            //エラーメッセージが詰まったリストをviewに送る
            mav.addObject("errorMessages", errorMessages);
            // 画面遷移先を指定
            mav.setViewName("/userAdd");
            return mav;
        }

        //妥当性チェック②支社と部署の組み合わせが不正の場合にエラーメッセージ
        //combinationメソッドで組み合わせのチェックをする
        if (!combination(addUser.getBranchId(), addUser.getDepartmentId())) {
            //エラーメッセージを入れる用のリストを作っておく
            List<String> errorMessages = new ArrayList<String>();
            errorMessages.add(E0023);
            //エラーメッセージが詰まったリストをviewに送る
            mav.addObject("errorMessages", errorMessages);
            // 画面遷移先を指定
            mav.setViewName("/userAdd");
            return mav;
        }

        //重複チェック→同じアカウント名が存在したらエラーメッセージ
        if (userService.existCheck(addUser.getAccount())) {
            //エラーメッセージを入れる用のリストを作っておく
            List<String> errorMessages = new ArrayList<String>();
            errorMessages.add(E0015);
            //エラーメッセージが詰まったリストをviewに送る
            mav.addObject("errorMessages", errorMessages);
            // 画面遷移先を指定
            mav.setViewName("/userAdd");
            return mav;
        }

        //今の時間をセット
        LocalDateTime now = LocalDateTime.now();
        addUser.setCreatedDate(now);
        addUser.setUpdatedDate(addUser.getCreatedDate());

        //入力された情報を登録しに行く
        userService.saveUser(addUser);
        //ユーザー管理画面へリダイレクト
        return new ModelAndView("redirect:/userAdmin");
    }

    /*
     * combinationメソッド UserEditControllerでも使うからstatic
     */
    public static boolean combination(int branchId, int departmentId) {
        if ((branchId == 1 && departmentId == 1) || (branchId == 1 && departmentId == 2) || (branchId >= 2 && departmentId >= 3)) {
            return true;
        } else {
            return false;
        }
    }

}


package com.example.IS.controller;

import com.example.IS.controller.form.UserForm;
import com.example.IS.groups.EditGroup;
import com.example.IS.service.BranchService;
import com.example.IS.service.DepartmentService;
import com.example.IS.service.UserService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.IS.constFolder.ErrorMessage.*;

@Controller
public class UserEditController {
    @Autowired
    UserService userService;

    @Autowired
    BranchService branchService;

    @Autowired
    DepartmentService departmentService;
    @Autowired
    HttpServletRequest request;

    /*
     * ユーザ編集画面表示処理
     */
    @GetMapping("/userEdit/{id}")
    public ModelAndView editUser(@PathVariable int id) {
        ModelAndView mav = new ModelAndView();
        UserForm editUser = userService.findEditUser(id);

        if(editUser == null){
            HttpSession session = request.getSession(true);
            //エラーメッセージを入れる用のリストを作っておく
            List<String> errorMessages = new ArrayList<String>();
            errorMessages.add(E0025);
            //エラーメッセージが詰まったセッションを用意
            session.setAttribute("errorMessages", errorMessages);
            //ユーザ管理画面へリダイレクト
            return new ModelAndView("redirect:/userAdmin");
        }

        mav.addObject("editUser", editUser);
        mav.setViewName("userEdit");
        return mav;
    }

    /*
     * ユーザ編集処理
     */
    @PutMapping("/userEdit")
    public ModelAndView editUser(@Validated({EditGroup.class}) @ModelAttribute("editUser") UserForm editUser, BindingResult result) {
        ModelAndView mav = new ModelAndView();
        //エラーメッセージを入れる用のリストを作っておく
        List<String> errorMessages = new ArrayList<String>();
        //リクエストパラメータの必須＆文字数＆半角文字チェック（パスワード以外）
        if (result.hasErrors()) {
            //result.getFieldErrors()はresultの持つ全エラーを要素にしたリスト→型はList<FieldError>
            //要素を1つ取り出してerrorに代入して処理→全ての要素が尽きるまで繰り返す
            for (FieldError error : result.getFieldErrors()) {
                //error.getDefaultMessage()で取得したエラーメッセージをリストに追加
                errorMessages.add(error.getDefaultMessage());
            }
        }

        //パスワードの入力がある場合は文字数&半角チェック
        String password = editUser.getPassword();
        if (!StringUtils.isEmpty(password) &&
                ((password.length() < 6 || password.length() > 20) || password.matches("^[!-~]$"))) {
            errorMessages.add(E0017);
        }

        if (errorMessages.size() >= 1){
            //エラーメッセージが詰まったリストをviewに送る
            mav.addObject("errorMessages", errorMessages);
            // 画面遷移先を指定
            mav.setViewName("/userEdit");
            return mav;
        }

        //妥当性チェック①パスワードと確認用パスワードが異なる時にエラーメッセージ
        if (!editUser.getPassword().equals(editUser.getCheckPassword())) {
            errorMessages.add(E0018);
            //エラーメッセージが詰まったリストをviewに送る
            mav.addObject("errorMessages", errorMessages);
            // 画面遷移先を指定
            mav.setViewName("/userEdit");
            return mav;
        }

        //妥当性チェック②支社と部署の組み合わせが不正の場合にエラーメッセージ
        //combinationメソッドで組み合わせのチェックをする
        if (!UserAddController.combination(editUser.getBranchId(), editUser.getDepartmentId())) {
            errorMessages.add(E0023);
            //エラーメッセージが詰まったリストをviewに送る
            mav.addObject("errorMessages", errorMessages);
            // 画面遷移先を指定
            mav.setViewName("/userEdit");
            return mav;
        }

        //重複チェック→同じアカウント名が存在かつidが異なる場合エラーメッセージ
        if (userService.existCheck(editUser.getAccount()) && (editUser.getId() != userService.findId(editUser.getAccount()))) {
            errorMessages.add(E0015);
            //エラーメッセージが詰まったリストをviewに送る
            mav.addObject("errorMessages", errorMessages);
            // 画面遷移先を指定
            mav.setViewName("/userEdit");
            return mav;
        }

        //今の時間をセット
        LocalDateTime now = LocalDateTime.now();
        editUser.setUpdatedDate(now);
        //入力された情報を更新しに行く
        userService.saveUser(editUser);
        //ユーザー管理画面へリダイレクト
        return new ModelAndView("redirect:/userAdmin");
    }

}


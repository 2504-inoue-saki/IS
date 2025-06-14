package com.example.IS.controller;

import com.example.IS.controller.form.UserForm;
import com.example.IS.groups.LoginGroup;
import com.example.IS.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static com.example.IS.constFolder.ErrorMessage.*;

@Controller
public class LoginController {
    //インスタンスの注入
    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;
    @Autowired
    HttpServletRequest request;

    /*
     * ログイン画面表示
     */
    @GetMapping("/login")
    public ModelAndView loginContent() {
        ModelAndView mav = new ModelAndView();

        //ログインフィルターの処理
        //セッションの獲得
        HttpSession session = request.getSession(true);
        //セッション内にフィルターメッセージがある時フィルターに引っかかる
        if (session.getAttribute("filterMessage") != null) {
            //エラーメッセージを入れる用のリストを作っておく
            List<String> errorMessages = new ArrayList<>();
            //フィルターメッセージをエラーメッセージ用リストに入れる（List<String>に合わせる）
            errorMessages.add((String) session.getAttribute("filterMessage"));
            //セッション内のフィルターメッセージを消す
            session.removeAttribute("filterMessage");
            //エラーメッセージが詰まったリストをviewに送る
            mav.addObject("errorMessages", errorMessages);
        }
        //空のloginUserをviewに送る
        UserForm loginUser = new UserForm();
        mav.addObject("loginUser", loginUser);

        // ヘッダー表示処理（井上追加）
        // ログイン画面なので、ログアウトボタンは非表示にする
        mav.addObject("isLoginPage", true);

        // 画面遷移先を指定
        mav.setViewName("/login");
        return mav;
    }

    /*
     * ログイン処理
     */
    @PostMapping("/loginProcess")
    //リクエストパラメータの取得
    public ModelAndView loginContent(@Validated({LoginGroup.class}) @ModelAttribute("loginUser") UserForm requestLogin, BindingResult result) {
        ModelAndView mav = new ModelAndView();

        //必須チェック
        if (result.hasErrors()) {
            //エラーメッセージを入れる用のリストを作っておく
            List<String> errorMessages = new ArrayList<>();
            //result.getFieldErrors()はresultの持つ全エラーを要素にしたリスト→型はList<FieldError>
            //要素を1つ取り出してerrorに代入して処理→全ての要素が尽きるまで繰り返す
            for (FieldError error : result.getFieldErrors()) {
                //error.getDefaultMessage()で取得したエラーメッセージをリストに追加
                errorMessages.add(error.getDefaultMessage());
            }
            //エラーメッセージが詰まったリストをviewに送る
            mav.addObject("errorMessages", errorMessages);
            // 画面遷移先を指定
            mav.setViewName("/login");
            return mav;
        }

        // 入力されたアカウントとパスワードが存在するか確認しに行く
        UserForm loginUser = userService.findLoginUser(requestLogin);

        //ログインユーザー情報チェック
        if (loginUser == null || loginUser.getIsStopped() == 1) {
            //エラーメッセージを入れる用のリストを作っておく
            List<String> errorMessages = new ArrayList<String>();
            errorMessages.add(E0003);
            mav.addObject("errorMessages", errorMessages);
            // 画面遷移先を指定
            mav.setViewName("/login");
            return mav;
        }
        //チェックに引っかからなければ、ログイン情報を保持＆ホーム画面へリダイレクト(旭)
        HttpSession session = request.getSession(true);
        session.setAttribute("loginUser", loginUser);
        return new ModelAndView("redirect:/IS/");
    }

    /*
     * ログアウト機能
     */
    @PostMapping("/logout")
    public ModelAndView logoutContent() {
        HttpSession session = request.getSession(true);
        //ログインユーザが存在しない場合→エラーメッセージをホーム画面に表示(旭)
        if (session.getAttribute("loginUser") == null) {
            session.setAttribute("filterMessage", E0025);
            return new ModelAndView("redirect:/IS/");
        }
        session.removeAttribute("loginUser");
        return new ModelAndView("redirect:/login");
    }

}

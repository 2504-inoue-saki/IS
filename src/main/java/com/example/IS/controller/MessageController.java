package com.example.IS.controller;

import com.example.IS.controller.form.MessageForm;
import com.example.IS.controller.form.UserForm;
import com.example.IS.groups.NewGroup;
import com.example.IS.service.MessageService;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
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

@Controller
public class MessageController {
    //インスタンスの注入
    @Autowired
    MessageService messageService;
    @Autowired
    HttpServletRequest request;

    /*
     * 新規投稿画面表示（鈴木）
     */
    @GetMapping("/new")
    public ModelAndView newContent() {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession(true);

        //空のメッセージフォームをセット（初期値は空）
        MessageForm messageForm = new MessageForm();
        mav.addObject("message", messageForm);

        //新規投稿登録処理でエラーがあった際のエラーメッセージ表示処理＆入力内容保持
        if (session.getAttribute("errorMessages") != null) {
            //もってきたエラーメッセージをエラーメッセージ用リストに入れる（List<String>に合わせる）
            List<String> errorMessages = (List<String>) session.getAttribute("errorMessages");
            //セッションからエラーメッセージを削除
            session.removeAttribute("errorMessages");
            //エラーメッセージが詰まったリストをviewに送る
            mav.addObject("errorMessages", errorMessages);

            MessageForm errorMessageForm = (MessageForm)session.getAttribute("message");
            //セッションから削除
            session.removeAttribute("message");
            //上書き
            mav.addObject("message", errorMessageForm);
        }
        
        // 画面遷移先を指定
        mav.setViewName("/new");
        return mav;
    }

    /*
     * 新規投稿登録処理（鈴木）
     */
    @PostMapping("/add")
    //リクエストパラメータの取得
    public ModelAndView addContent(@Validated({NewGroup.class}) @ModelAttribute("message") MessageForm messageForm, BindingResult result) {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession(true);

        //リクエストパラメータの必須＆文字数チェック
        if (result.hasErrors()) {
            //エラーメッセージを入れる用のリストを作っておく
            List<String> errorMessages = new ArrayList<String>();
            //result.getFieldErrors()はresultの持つ全エラーを要素にしたリスト→型はList<FieldError>
            //要素を1つ取り出してerrorに代入してリストに追加→全ての要素が尽きるまで繰り返す
            for (FieldError error : result.getFieldErrors()) {
                //error.getDefaultMessage()で取得したエラーメッセージをリストに追加
                errorMessages.add(error.getDefaultMessage());
            }
            //エラーメッセージが詰まったセッションを用意
            session.setAttribute("errorMessages", errorMessages);
            session.setAttribute("message", messageForm);
            //新規投稿画面へリダイレクト（何でリダイレクトなの？）
            return new ModelAndView("redirect:/new");

//            下は確認用（鈴木）
//            mav.addObject("errorMessages", errorMessages);
//            mav.setViewName("/new");
//            return mav;
        }

        //今の時間をセット
        LocalDateTime now = LocalDateTime.now();
        messageForm.setCreatedDate(now);
        messageForm.setUpdatedDate(messageForm.getCreatedDate());

        //投稿者IDのセット
        UserForm loginUser = (UserForm) session.getAttribute("loginUser");
        messageForm.setUserId(loginUser.getId());

        // 入力された情報を登録しに行く
        messageService.addMessage(messageForm);

        //ホーム画面へリダイレクト
        return new ModelAndView("redirect:/");
    }

    /*
     * 投稿削除処理
     */
    @DeleteMapping("/message/delete/{id}")
    public ModelAndView deleteMessage(@PathVariable Integer id) {

        // テーブルから投稿を削除
        messageService.deleteMessage(id);
        // rootへリダイレクト
        return new ModelAndView("redirect:/");
    }
}

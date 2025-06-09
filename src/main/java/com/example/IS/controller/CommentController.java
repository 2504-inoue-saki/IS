package com.example.IS.controller;

import com.example.IS.controller.form.CommentForm;
import com.example.IS.controller.form.UserForm;
import com.example.IS.service.CommentService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.IS.constFolder.ErrorMessage.*;

@Controller
public class CommentController {
    //インスタンスの注入
    @Autowired
    CommentService commentService;
    @Autowired
    HttpServletRequest request;

    /*
     * 返信登録処理（鈴木）
     */
    @PostMapping("/comment")
    //リクエストパラメータの取得
    public ModelAndView addContent(@RequestParam(name = "commentText", required = false) String text,
                                   @RequestParam(name = "messageId", required = false) Integer messageId) {
        ModelAndView mav = new ModelAndView();
        HttpSession session = request.getSession(true);
        CommentForm commentForm = new CommentForm();
        //エラーメッセージを入れる用のリストを作っておく
        List<String> errorMessages = new ArrayList<String>();

        //リクエストパラメータの必須チェック
        if (StringUtils.isBlank(text)) {
            errorMessages.add(E0004);
            //エラーメッセージが詰まったセッションを用意
            session.setAttribute("errorMessages", errorMessages);
            //ホーム画面へリダイレクト
            return new ModelAndView("redirect:/IS/");
        }
        //リクエストパラメータの文字数チェック
        if (text.length() > 500) {
            errorMessages.add(E0005);
            //エラーメッセージが詰まったセッションを用意
            session.setAttribute("errorMessages", errorMessages);
            //ホーム画面へリダイレクト
            return new ModelAndView("redirect:/IS/");
        }

        //返信内容のテキストをセット
        commentForm.setText(text);
        //メッセージIDをセット
        commentForm.setMessageId(messageId);

        //今の時間をセット
        LocalDateTime now = LocalDateTime.now();
        commentForm.setCreatedDate(now);
        commentForm.setUpdatedDate(commentForm.getCreatedDate());

        //投稿者IDのセット
        UserForm loginUser = (UserForm) session.getAttribute("loginUser");
        commentForm.setUserId(loginUser.getId());

        // 入力された情報を登録しに行く
        commentService.addComment(commentForm);

        //ホーム画面へリダイレクト
        return new ModelAndView("redirect:/IS/");
    }

    /*
     * 返信削除処理
     */
    @DeleteMapping("/comment/delete/{id}")
    public ModelAndView deleteComment(@PathVariable Integer id) {

        // テーブルから投稿を削除
        commentService.deleteComment(id);
        // rootへリダイレクト
        return new ModelAndView("redirect:/IS/");
    }
}

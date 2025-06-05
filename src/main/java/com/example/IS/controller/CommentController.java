package com.example.IS.controller;

import com.example.IS.controller.form.CommentForm;
import com.example.IS.controller.form.MessageForm;
import com.example.IS.controller.form.UserForm;
import com.example.IS.groups.NewGroup;
import com.example.IS.repository.entity.Comment;
import com.example.IS.service.CommentService;
import com.example.IS.service.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

//        //リクエストパラメータの必須＆文字数チェック
//        if(result.hasErrors()){
//            //エラーメッセージを入れる用のリストを作っておく
//            List<String> errorMessages = new ArrayList<String>();
//            //result.getFieldErrors()はresultの持つ全エラーを要素にしたリスト→型はList<FieldError>
//            //要素を1つ取り出してerrorに代入してリストに追加→全ての要素が尽きるまで繰り返す
//            for(FieldError error : result.getFieldErrors()){
//                //error.getDefaultMessage()で取得したエラーメッセージをリストに追加
//                errorMessages.add(error.getDefaultMessage());
//            }
//            //エラーメッセージが詰まったセッションを用意
//            session.setAttribute("errorMessages", errorMessages);
//            //新規投稿画面へリダイレクト
//            return new ModelAndView("redirect:/");
//        }
        //メッセージIDをセット
        commentForm.setMessageId(messageId);
        //返信内容のテキストをセット
        commentForm.setText(text);

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
        return new ModelAndView("redirect:/");
    }
}

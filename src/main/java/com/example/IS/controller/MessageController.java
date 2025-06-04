//package com.example.IS.controller;
//
//import com.example.IS.controller.form.MessageForm;
//import com.example.IS.service.MessageService;
//import jakarta.servlet.http.HttpSession;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Controller
//public class MessageController {
//    //インスタンスの注入
//    @Autowired
//    MessageService messageService;
//
//    /*
//     * 新規投稿画面表示（鈴木）
//     */
//    @GetMapping("/new")
//    public ModelAndView newContent() {
//        ModelAndView mav = new ModelAndView();
//        //セッション内のエラーメッセージリストを取り出して新規のerrorMessagesリストに入れる
//        List<String> errorMessages = (List<String>) session.getAttribute("errorMessages");
//        //セッションからエラーメッセージリストを消すメソッド
//        errorMethod(errorMessages);
//
//        MessageForm messageForm = new MessageForm();
//        mav.addObject("message", messageForm);
//        mav.addObject("errorMessages", errorMessages);
//        mav.setViewName("/new");
//        return mav;
//    }
//
//    /*
//     * セッション内からエラーメッセージリストを削除
//     */
//    public void errorMethod(List<String> errorMessages){
//        if(errorMessages != null){
//            session.removeAttribute("errorMessages");
//        }
//    }
//
//    /*
//     * 新規投稿登録処理（鈴木）
//     */
//    @PostMapping("/add")
//    //リクエストパラメータの取得
//    public ModelAndView addContent(@Valid @ModelAttribute("message") MessageForm messageForm, BindingResult result) {
//        ModelAndView mav = new ModelAndView();
//
//        //リクエストパラメータの入力チェック
//        if(result.hasErrors()) {
//            List<String> errorMessages = new ArrayList<String>();
//            //result.getFieldErrors()はresultの持つ全エラーを要素にしたリスト→型はList<FieldError>
//            //要素を1つ取り出してerrorに代入して処理→全ての要素が尽きるまで繰り返す
//            for(FieldError error : result.getFieldErrors()){
//                //error.getDefaultMessage()で取得したエラーメッセージをリストに追加
//                errorMessages.add(error.getDefaultMessage());
//            }
//            //エラーメッセージが詰まったセッションを用意
//            session.setAttribute("errorMessages", errorMessages);
//            // 画面遷移先を指定
//            return new ModelAndView("redirect:/add");
//        }
//
//        //今の時間をセット
//        LocalDateTime now = LocalDateTime.now();
//        messageForm.setCreatedDate(now);
//        messageForm.setUpdatedDate(messageForm.getCreatedDate());
//
//        //投稿者IDのセット
//        UserForm loginUser = (UserForm) session.getAttribute("loginUser");
//        messageForm.setUserId(loginUser.getId());
//
//        // 入力された情報を登録しに行く
//        messageService.addMessage(messageForm);
//
//        //ホーム画面へリダイレクト
//        return new ModelAndView("redirect:/");
//    }
//
//    /*
//     * 投稿削除処理
//     */
//    @DeleteMapping("/delete/{id}")
//    public ModelAndView deleteContent(@PathVariable Integer id) {
//        // 投稿をテーブルに格納
//        messageService.deleteMessage(id);
//        // rootへリダイレクト
//        return new ModelAndView("redirect:/");
//    }
//}

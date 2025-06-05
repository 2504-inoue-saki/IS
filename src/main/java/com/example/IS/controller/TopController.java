package com.example.IS.controller;

import com.example.IS.controller.form.CommentForm;
import com.example.IS.controller.form.MessageForm;
import com.example.IS.dto.UserComment;
import com.example.IS.dto.UserMessage;
import com.example.IS.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TopController {
    @Autowired
    BranchService branchService;
    @Autowired
    CommentService commentService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;
    @Autowired
    HttpServletRequest request;

    /*
     * ホーム画面表示処理
     */
    @GetMapping("/")
    public ModelAndView top(@RequestParam(name = "start", required = false) LocalDate start,
                            @RequestParam(name = "end", required = false) LocalDate end,
                            @RequestParam(name = "category", required = false) String category) {
        ModelAndView mav = new ModelAndView();
        //権利者フィルターの処理
        //セッションの獲得
        HttpSession session = request.getSession(true);
        //セッション内にフィルターメッセージがある時フィルターに引っかかる
        if (session.getAttribute("filterMessage") != null){
            //エラーメッセージを入れる用のリストを作っておく
            List<String> errorMessages = new ArrayList<>();
            //フィルターメッセージをエラーメッセージ用リストに入れる（List<String>に合わせる）
            errorMessages.add((String)session.getAttribute("filterMessage"));
            //セッション内のフィルターメッセージを消す
            session.removeAttribute("filterMessage");
            //エラーメッセージが詰まったリストをviewに送る
            mav.addObject("errorMessages", errorMessages);
        }

        List<UserMessage> contentData = messageService.findMessageWithUserByOrder(start, end, category);
        List<UserComment> commentData = commentService.findAllCommentWithUser();

        Object loginUser = session.getAttribute("loginUser");
        if (loginUser != null) {
            mav.addObject("loginUser", loginUser);
        }

        Object errorMessage = session.getAttribute("errorMessages");
        if (errorMessage != null) {
            mav.addObject("errorMessages", errorMessage);
            session.removeAttribute("errorMessages");
        }
        // 画面遷移先を指定 「現在のURL」/top へ画面遷移
        mav.setViewName("/top");
        // 投稿データオブジェクトを先ほどのcontentDataをModelAndView型の変数mavへ格納
        mav.addObject("contents", contentData);
        mav.addObject("comments", commentData);
        mav.addObject("commentForm", new CommentForm());
        mav.addObject("start", start);
        mav.addObject("end", end);

        return mav;
    }
}

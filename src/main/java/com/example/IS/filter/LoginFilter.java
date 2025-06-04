package com.example.IS.filter;

import java.io.IOException;

import com.example.IS.controller.form.UserForm;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import static com.example.IS.constFolder.ErrorMessage.*;

@WebFilter(urlPatterns = {"/"})
public class LoginFilter implements Filter {

    @Autowired
    HttpSession session;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        //ServletRequestをHttpServletRequestに型変更
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;

        //セッションからログインユーザの獲得
        UserForm loginUser = (UserForm)session.getAttribute("loginUser");

        //ログインユーザが存在していない場合エラーメッセージ表示→ログイン情報のヌルチェック
        if (loginUser == null) {
            session.setAttribute("errorMessages", E0024);
            new ModelAndView("redirect:/login");
        } else {
            // 通常実行
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig config) {
    }

    @Override
    public void destroy() {
    }
}

package com.example.IS.controller;

import com.example.IS.controller.form.UserForm;
import com.example.IS.service.BranchService;
import com.example.IS.service.DepartmentService;
import com.example.IS.service.UserService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.List;

@Controller
public class UserEditController {
    @Autowired
    UserService userService;

    @Autowired
    BranchService branchService;

    @Autowired
    DepartmentService departmentService;

//    /*
//     * ユーザ編集画面表示処理
//     */
//    @GetMapping("/user/edit/{id}")
//    public ModelAndView editUser(@PathVariable(required = false) String id) {
//        ModelAndView mav = new ModelAndView();
//        List<UserForm> editUser = userService.findEditUser(id);
//        mav.addObject("editUser", editUser);
//    }

    /*
     * ユーザ編集処理
     */

}


package cn.xilio.ql.repeater.biz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author xilio
 * @version 1.0
 * @description 转发器
 * @date 2024/3/2 19:50
 */
@Controller
@RequestMapping("/")
public class RepeaterController {

    @GetMapping("/{code}")
    public void dispatch(@PathVariable String code, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("404");
        //return "redirect:"+"https://www.baidu.com";
    }

    @GetMapping("404")
    public String notFound() {
        return "views/404";
    }
}

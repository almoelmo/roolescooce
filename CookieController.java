package com.example.roles.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class CookieController {
    @GetMapping("/setCookie")
    public String setCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("testCookie", "cookieValue");
        cookie.setMaxAge(24 * 60 * 60); // 1 day
        response.addCookie(cookie);
        return "cookieSet";
    }

    @GetMapping("/getCookie")
    public String getCookie(@CookieValue(value = "testCookie", defaultValue = "defaultCookieValue") String testCookie, Model model) {
        model.addAttribute("testCookie", testCookie);
        return "cookieInfo";
    }
ng("/setSessionAttribute") public String setSessionAttribute(HttpSession session) {
        session.setAttribute("testSessionAttribute", "sessionAttributeValue");
        return "sessionAttributeSet";
    }
    @GetMapping("/getSessionAttribute") public String getSessionAttribute(HttpSession session, Model model) {
        String testSessionAttribute = (String) session.getAttribute("testSessionAttribute");
        if (testSessionAttribute == null) {
            testSessionAttribute = "defaultSessionAttributeValue";
        }
        model.addAttribute("testSessionAttribute", testSessionAttribute);
        return "sessionAttributeInfo";
    }
}

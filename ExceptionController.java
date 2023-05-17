package com.example.roles.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import javax.naming.NoPermissionException;

public class ExceptionController {
    @ResponseStatus(value= HttpStatus.FORBIDDEN)
    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public String handleNoPermissionException(Model model, NoPermissionException nre) {
        model.addAttribute("exception", nre.getStackTrace());
        return "exception";
    }

    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public String handleNotFoundException(Model model, NoPermissionException nre) {
        model.addAttribute("exception", nre.getStackTrace());
        return "exception";
    }
}

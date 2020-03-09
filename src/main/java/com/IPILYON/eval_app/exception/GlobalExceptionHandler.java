package com.IPILYON.eval_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

public class GlobalExceptionHandler {

    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView illegalArgumentException(IllegalArgumentException iae, Map<String, Object> model) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", iae);
        mav.setViewName("erreur");
        return mav;
    }
}

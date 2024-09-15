package com.backendspringboot.blog.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
public class CustomErrorController implements ErrorController {

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping("/error")
    public ResponseEntity<Map<String, Object>> handleError(WebRequest webRequest) {
        // Get the default error attributes
        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());
        // Get the HTTP status
        HttpStatus status = HttpStatus.valueOf((Integer) errorAttributes.get("status"));
        
        // Return the error response
        return new ResponseEntity<>(errorAttributes, status);
    }

    // Deprecated method getErrorPath() needs to be overridden

    public String getErrorPath() {
        return "/error";
    }
}

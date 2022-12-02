package com.example.contoller;

import com.example.exp.AlreadyExistObject;
import com.example.exp.ItemNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandle {

    @ExceptionHandler({AlreadyExistObject.class, ItemNotFoundException.class})

    public ResponseEntity<?> handle(RuntimeException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
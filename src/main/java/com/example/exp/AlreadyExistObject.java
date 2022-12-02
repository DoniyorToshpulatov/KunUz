package com.example.exp;

public class AlreadyExistObject extends  RuntimeException{
    public AlreadyExistObject(String message) {
        super(message);
    }
}

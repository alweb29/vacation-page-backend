package com.example.vacationpagebackend.service;

import org.springframework.http.HttpStatus;

public final class ContextExceptionFactory {
    private ContextExceptionFactory() {

    }

    public static RuntimeException roomRecordNotFound(Long id){
        return new RuntimeException(HttpStatus.NOT_FOUND + "Room record not found with id: " + id);
    }

    public static RuntimeException roomRecordNotFound(int month){
        return new RuntimeException(HttpStatus.NOT_FOUND + "Room record not found with month of: " + month);
    }
}

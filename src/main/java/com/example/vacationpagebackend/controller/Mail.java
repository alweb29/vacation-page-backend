package com.example.vacationpagebackend.controller;

import com.example.vacationpagebackend.entity.RoomType;
import lombok.Data;

@Data
public class Mail {
    private String nameAndLastName;
    private String email;
    private String phoneNo;
    private RoomType roomType;
    private String message;

    @Override
    public String toString() {
        return "Mail{" +
                "nameAndLastName='" + nameAndLastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", roomType=" + roomType +
                ", message='" + message + '\'' +
                '}';
    }
}

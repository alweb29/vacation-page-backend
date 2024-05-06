package com.example.vacationpagebackend.controller;

import com.example.vacationpagebackend.entity.RoomType;
import lombok.Data;

@Data
public class Mail {
    private String nameAndLastName;
    private String email;
    private String phoneNo;
    private int roomType;
    private String message;

    @Override
    public String toString() {
        return
                "Imie i nazwisko: " + nameAndLastName + '\n' +
                "email: '" + email + '\n' +
                "numer telefonu:" + phoneNo + '\n' +
                "rodzaj pokoju: " + roomType + '\n' +
                "wiadomość: \n" + message +"\n\n" +
                "Ja, " +nameAndLastName +" wyrażam zgodę na przetwarzanie moich danych na potrzeby kontaktu";
    }
}

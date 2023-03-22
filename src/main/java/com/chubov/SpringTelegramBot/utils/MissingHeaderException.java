package com.chubov.SpringTelegramBot.utils;

public class MissingHeaderException extends RuntimeException {
    //  Custom exception for catching when JWT doesn't send in Header "Authorization"
    public MissingHeaderException(String message) {
        super(message);
    }
}
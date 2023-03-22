package com.chubov.SpringTelegramBot.utils;

public class BadTokenException extends RuntimeException {
    //  Custom exception for catching when bad JWToken
    public BadTokenException(String message) {
        super(message);
    }
}
package com.spata14.trelloproject.card.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CardException extends RuntimeException{
    private final CardErrorCode cardErrorCode;

    public String getMessage(){
        return cardErrorCode.getMessage();
    }

}

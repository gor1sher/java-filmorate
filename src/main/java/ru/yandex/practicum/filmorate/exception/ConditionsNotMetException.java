package ru.yandex.practicum.filmorate.exception;

public class ConditionsNotMetException extends RuntimeException{

    public ConditionsNotMetException(String str){
        super(str);
    }
}

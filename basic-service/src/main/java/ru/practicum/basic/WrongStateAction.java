package ru.practicum.basic;

public class WrongStateAction extends RuntimeException {
    public WrongStateAction(String message) {
        super(message);
    }
}

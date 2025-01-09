package com.internal.assignment.internal.exceptions;

public class TransactionNotFoundException extends RuntimeException{
    public TransactionNotFoundException(String message)
    {
        super(message);
    }
}

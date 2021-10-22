package com.falabella.stock.exceptions;

public class StockFullException extends RuntimeException {
    public StockFullException(String message) {
        super(message);
    }
}

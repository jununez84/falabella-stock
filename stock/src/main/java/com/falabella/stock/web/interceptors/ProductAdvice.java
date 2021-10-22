package com.falabella.stock.web.interceptors;

import com.falabella.stock.exceptions.ProductNotFoundException;
import com.falabella.stock.exceptions.StockFullException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ProductAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handlerException(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getAllErrors().stream().findFirst().get().getDefaultMessage();
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlerException(ProductNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(StockFullException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public String handlerException(StockFullException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public String handlerException(Exception ex) {
        return ex.getMessage();
    }

}

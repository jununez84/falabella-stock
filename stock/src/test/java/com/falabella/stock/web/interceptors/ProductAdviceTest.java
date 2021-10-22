package com.falabella.stock.web.interceptors;

import com.falabella.stock.exceptions.ProductNotFoundException;
import com.falabella.stock.exceptions.StockFullException;
import com.falabella.stock.web.interceptors.ProductAdvice;
import org.hibernate.HibernateException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class ProductAdviceTest {

    private ProductAdvice productAdvice;

    @Before
    public void setup() {
        this.productAdvice = new ProductAdvice();
    }

    @Test
    public void handlerDefaultException() {
        HibernateException ex = new HibernateException("Se alcanzó el maximo de productos en el stock");
        String response = this.productAdvice.handlerException(ex);

        Assert.assertEquals(response, "Se alcanzó el maximo de productos en el stock");
    }

    @Test
    public void handlerProductNotFoundException() {
        ProductNotFoundException ex = new ProductNotFoundException("Producto no encontrado");
        String response = this.productAdvice.handlerException(ex);

        Assert.assertEquals(response, "Producto no encontrado");
    }

    @Test
    public void handlerStockFullException() {
        StockFullException ex = new StockFullException("El stock alcanzó el limite");
        String response = this.productAdvice.handlerException(ex);

        Assert.assertEquals(response, "El stock alcanzó el limite");
    }

    public void handlerException_MethodArgumentNotValidException() {
        MethodArgumentNotValidException ex = Mockito.mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        ObjectError objectError = Mockito.mock(ObjectError.class);

        Mockito.when(objectError.toString()).thenReturn("List<ObjectError>");

        Mockito.when(bindingResult.getAllErrors())
                .thenReturn(Arrays.asList(objectError));

        Mockito.when(ex.getBindingResult()).thenReturn(bindingResult);
        Mockito.when(objectError.getDefaultMessage()).thenReturn("Nombre es requerido");

        String response = this.productAdvice.handlerException(ex);

        Assert.assertEquals(response, "Nombre es requerido");
    }

}
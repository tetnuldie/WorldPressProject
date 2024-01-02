package org.example.pages;

public class PageException extends Exception{
    public PageException(){
        super();
    }

    public PageException(String message){
        super(message);
    }

    public PageException(Exception e){
        super(e);
    }

    public PageException(String message, Exception e){
        super(message, e);
    }
}

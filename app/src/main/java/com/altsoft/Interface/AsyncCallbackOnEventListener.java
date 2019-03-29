package com.altsoft.Interface;

public interface AsyncCallbackOnEventListener<T> {
    public void onSuccess(T object);
    public void onFailure(Exception e);
}
package com.sfac.geniusdirecruit.common.entity;

public class ResultEntity<T> {
    private int status;
    private String message;
    private T object;
    private T src;

    public ResultEntity() {
    }

    public ResultEntity(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResultEntity(int status, String message, T object) {
        this.status = status;
        this.message = message;
        this.object = object;
    }

    public ResultEntity(int status, String message, T object, T src) {
        this.status = status;
        this.message = message;
        this.object = object;
        this.src = src;
    }

    public T getSrc() {
        return src;
    }

    public void setSrc(T src) {
        this.src = src;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public enum ResultStatus {
        SUCCESS(200), FAILED(500);

        public int status;

        private ResultStatus(int status) {
            this.status = status;
        }
    }
}

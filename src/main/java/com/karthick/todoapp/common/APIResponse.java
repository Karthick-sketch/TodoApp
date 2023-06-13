package com.karthick.todoapp.common;

public class APIResponse {
    private int statusCode;
    private Object data;
    private Object error;

    public APIResponse() {
        this.statusCode = 200;
        this.data = null;
        this.error = null;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }
}

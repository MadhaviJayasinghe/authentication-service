package com.dexlk.authentication.authenticationservice.model;

public class ValidationResponse {
    private String response;

    public ValidationResponse() {
    }

    public ValidationResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}

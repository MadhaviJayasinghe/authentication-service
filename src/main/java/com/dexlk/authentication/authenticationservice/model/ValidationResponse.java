package com.dexlk.authentication.authenticationservice.model;

public class ValidationResponse {
    private String response;
    private String userId;

    public ValidationResponse() {
    }

    public ValidationResponse(String response, String userId) {
        this.response = response;
        this.userId = userId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

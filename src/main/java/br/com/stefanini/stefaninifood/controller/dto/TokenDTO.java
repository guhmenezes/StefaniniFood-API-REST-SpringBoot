package br.com.stefanini.stefaninifood.controller.dto;

public class TokenDTO {

    private String token;
    private String type;
    private String id;

    public TokenDTO(String token, String type, String id) {
        this.token = token;
        this.type = type;
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

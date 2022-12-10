package pl.opinion_collector.backend.logic.user.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    @JsonIgnore
    private String email;
    @JsonIgnore
    private List<String> roles;

    public JwtResponse(String accessToken, String email, List<String> roles) {
        this.token = accessToken;
        this.email = email;
        this.roles = roles;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }
}
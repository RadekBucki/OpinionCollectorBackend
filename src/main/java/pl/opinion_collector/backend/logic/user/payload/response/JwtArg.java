package pl.opinion_collector.backend.logic.user.payload.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import pl.opinion_collector.backend.logic.user.dto.UserDto;

@Getter
@Setter
public class JwtArg {
    @ApiModelProperty(notes = "JWT Token", example =
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJta0BvMi5wbCIsIml" +
                    "hdCI6MTY3MDc2MDk0OSwiZXhwIjoxNjcwODQ3MzQ5fQ.vgQ" +
                    "-aLC1uPv7NixiqHET2lLxjZaJY4W-z0lU9gZ6Z29dKeDrO66FG" +
                    "w8yxC4mkpm9LhzEbyGpcy8YREJNuIutQA")
    private String token;

    @ApiModelProperty(notes = "Token type", example = "Bearer")
    private String type = "Bearer";

    @ApiModelProperty(notes = "User data")
    private UserDto user;

    public JwtArg(String accessToken, UserDto user) {
        this.token = accessToken;
        this.user = user;
    }
}
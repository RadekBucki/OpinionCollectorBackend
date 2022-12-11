package pl.opinion_collector.backend.logic.user.payload.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtArg {
    @ApiModelProperty(notes = "JWT Token")
    private String token;

    @ApiModelProperty(notes = "Token type")
    private String type = "Bearer";

    public JwtArg(String accessToken) {
        this.token = accessToken;
    }
}
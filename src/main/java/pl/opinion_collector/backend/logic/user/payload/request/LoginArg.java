package pl.opinion_collector.backend.logic.user.payload.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginArg {
    @ApiModelProperty(notes = "User username", example = "example@gmail.com")
    @NonNull
    @NotBlank
    @Email
    private String email;
    @ApiModelProperty(notes = "User password", example = "123abC#")
    @NonNull
    @NotBlank
    private String password;
}

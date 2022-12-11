package pl.opinion_collector.backend.logic.user.payload.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignupArg {

    @NotBlank
    @NonNull
    @ApiModelProperty(notes = "User first name", example = "John")
    private String firstName;

    @NotBlank
    @NonNull
    @ApiModelProperty(notes = "User last name", example = "Smith")
    private String lastName;

    @NotBlank
    @NonNull
    @Size(max = 50)
    @Email
    @ApiModelProperty(notes = "User email", example = "example@gmail.com")
    private String email;

    @ApiModelProperty(notes = "Is user admin", example = "false")
    private Boolean isAdmin = false;

    @NotBlank
    @NonNull
    @ApiModelProperty(notes = "User profile picture Url", example = "https://pl.pinterest.com/pin/327848047887112192/")
    private String pictureUrl;

    @NotBlank
    @NonNull
    @Size(min = 6, max = 40)
    @ApiModelProperty(notes = "User password", example = "123abC#")
    private String password;
}

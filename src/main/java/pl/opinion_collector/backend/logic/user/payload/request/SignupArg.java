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
    @ApiModelProperty(notes = "User first name")
    private String firstName;

    @NotBlank
    @NonNull
    @ApiModelProperty(notes = "User last name")
    private String lastName;

    @NotBlank
    @NonNull
    @Size(max = 50)
    @Email
    @ApiModelProperty(notes = "User email")
    private String email;

    @NotBlank
    @ApiModelProperty(notes = "Is user admin")
    private boolean isAdmin;

    @NotBlank
    @NonNull
    @ApiModelProperty(notes = "User profile picture Url")
    private String pictureUrl;

    @NotBlank
    @NonNull
    @Size(min = 6, max = 40)
    @ApiModelProperty(notes = "User password")
    private String password;
}

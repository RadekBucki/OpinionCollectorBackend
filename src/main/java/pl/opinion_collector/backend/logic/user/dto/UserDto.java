package pl.opinion_collector.backend.logic.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {
    @ApiModelProperty(notes = "User first name", example = "John")
    private String firstName;

    @ApiModelProperty(notes = "User last name", example = "Smith")
    private String lastName;

    @ApiModelProperty(notes = "User email", example = "example@gmail.com")
    private String email;

    @ApiModelProperty(notes = "Is user admin or standard user", example = "true")
    private Boolean isAdmin;

    @ApiModelProperty(notes = "Profile picture url", example = "https://pl.pinterest.com/pin/327848047887112192/")
    private String profilePictureUrl;
}

package pl.opinion_collector.backend.logic.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {
    @ApiModelProperty(notes = "User first name")
    private String firstName;

    @ApiModelProperty(notes = "User last name")
    private String lastName;

    @ApiModelProperty(notes = "User email")
    private String email;

    @ApiModelProperty(notes = "Is user admin")
    private Boolean isAdmin;

    @ApiModelProperty(notes = "Profile picture url")
    private String profilePictureUrl;
}

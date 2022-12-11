package pl.opinion_collector.backend.logic.user.controller;


import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.opinion_collector.backend.logic.user.UserFacade;
import pl.opinion_collector.backend.logic.user.dto.Mapper;
import pl.opinion_collector.backend.logic.user.dto.UserDto;
import pl.opinion_collector.backend.logic.user.wrapper.Role;
import pl.opinion_collector.backend.logic.user.wrapper.UserWrapper;
import pl.opinion_collector.backend.logic.user.payload.request.LoginArg;
import pl.opinion_collector.backend.logic.user.payload.request.SignupArg;
import pl.opinion_collector.backend.logic.user.payload.response.JwtArg;
import pl.opinion_collector.backend.logic.user.payload.response.MessageArg;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserFacadeController {
    @Autowired
    private UserFacade userFacade;
    private final static Mapper mapper = new Mapper();
    @ApiOperation(value = "Get all users and their data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 403, message = "U are not allowed to this resource"),
            @ApiResponse(code = 401, message = "U are not authorized")
    })
    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        return new ResponseEntity<>(userFacade.getAllUsers().stream()
                .map(mapper::mapUser)
                .collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @ApiOperation(value = "Register new user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Registration succeed"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 403, message = "U are not allowed to this resource"),
            @ApiResponse(code = 401, message = "U are not authorized")
    })
    @PostMapping("/register")
    public ResponseEntity<MessageArg> register(@ApiParam(name = "Registration request body",
                                                        value = "Body of the request")
                                          @RequestBody SignupArg registerRequest
            , @ApiParam(name = "HTTP Servlet Request", value = "Request information for HTTP servlets")
                                          HttpServletRequest httpServletRequest) {
        String headerToken = "";
        if (httpServletRequest.getHeader("Authorization") != null)
            headerToken = httpServletRequest.getHeader("Authorization").substring(7);

        if (registerRequest.isAdmin() && !headerToken.isBlank()
                && userFacade.getUserByToken(headerToken) != null) {
            if (userFacade.getUserByToken(headerToken).isAdmin()) {
                UserWrapper user = userFacade.registerAdmin(registerRequest.getFirstName(),
                        registerRequest.getLastName(),
                        registerRequest.getEmail(),
                        registerRequest.getPassword(),
                        registerRequest.getPictureUrl());
                return user != null
                        ? ResponseEntity.ok().body(new MessageArg("Admin has been registered"))
                        : ResponseEntity.badRequest().body(new MessageArg("Incorrect register input or email is taken"));
            }
        }
        if (registerRequest.isAdmin()) {
            return ResponseEntity.badRequest().body(new MessageArg("U have no permissions to add new admin"));
        }

        UserWrapper user = userFacade.register(registerRequest.getFirstName(),
                registerRequest.getLastName(),
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getPictureUrl());
        return  user != null ? ResponseEntity.ok().body(new MessageArg("Standard user has been registered"))
                             : ResponseEntity.badRequest().body(new MessageArg("Incorrect register input or email is taken"));
    }
    @ApiOperation(value = "Login to the user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Login succeed"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 403, message = "U are not allowed to this resource"),
            @ApiResponse(code = 401, message = "U are not authorized")
    })
    @GetMapping("/login")
    public ResponseEntity<JwtArg> login(@ApiParam(name = "Login request", value = "Login request information")
                                       @Valid @RequestBody LoginArg loginRequest) {
        String token = userFacade.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok().body(new JwtArg(token));
    }
    @ApiOperation(value = "Update the user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Update succeed"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 403, message = "U are not allowed to this resource"),
            @ApiResponse(code = 401, message = "U are not authorized")
    })
    @PutMapping("/update")
    public ResponseEntity<MessageArg> update(@ApiParam(name = "Update request",
            value = "Update request body needed to update user")
            @RequestBody SignupArg updateRequest) {
        UserWrapper user = userFacade.updateUser(updateRequest.getId(), updateRequest.getFirstName(),
                updateRequest.getLastName(), updateRequest.getEmail(),
                updateRequest.getPassword(), updateRequest.getPictureUrl(),
                updateRequest.isAdmin());
        return user == null ? ResponseEntity.badRequest().body(new MessageArg("User data has not been modified"))
                            : ResponseEntity.ok().body(new MessageArg("User data has been modified"));
    }

}

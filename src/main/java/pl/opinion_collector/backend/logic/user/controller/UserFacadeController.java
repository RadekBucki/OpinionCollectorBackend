package pl.opinion_collector.backend.logic.user.controller;


import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.opinion_collector.backend.logic.user.UserFacade;
import pl.opinion_collector.backend.logic.user.UserFacadeImpl;
import pl.opinion_collector.backend.logic.user.model.Role;
import pl.opinion_collector.backend.logic.user.model.User;
import pl.opinion_collector.backend.logic.user.payload.request.LoginRequest;
import pl.opinion_collector.backend.logic.user.payload.request.SignupRequest;
import pl.opinion_collector.backend.logic.user.payload.response.JwtResponse;
import pl.opinion_collector.backend.logic.user.payload.response.MessageResponse;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserFacadeController {
    @Autowired
    private UserFacade userFacade;

    @ApiOperation(value = "Get all users and their data")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 403, message = "U are not allowed to this resource"),
            @ApiResponse(code = 401, message = "U are not authorized")
    })
    @GetMapping
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok().body(userFacade.getAllUsers());
    }

    @ApiOperation(value = "Register new user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Registration succeed"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 403, message = "U are not allowed to this resource"),
            @ApiResponse(code = 401, message = "U are not authorized")
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@ApiParam(name = "Registration request body", value = "Body of the request")
                                          @RequestBody SignupRequest registerRequest
            , @ApiParam(name = "HTTP Servlet Request", value = "Request information for HTTP servlets")
                                          HttpServletRequest httpServletRequest) {
        String headerToken = "";
        if (httpServletRequest.getHeader("Authorization") != null)
            headerToken = httpServletRequest.getHeader("Authorization").substring(7);

        if (registerRequest.isAdmin() && !headerToken.isBlank() && userFacade.getUserByToken(headerToken) != null) {
            if (userFacade.getUserByToken(headerToken).getRoles().contains(Role.ROLE_ADMIN)) {
                User user = userFacade.registerAdmin(registerRequest.getFirstName(),
                        registerRequest.getLastName(),
                        registerRequest.getEmail(),
                        registerRequest.getPassword(),
                        registerRequest.getPictureUrl());
                return user != null
                        ? ResponseEntity.ok().body(new MessageResponse("Admin has been registered"))
                        : ResponseEntity.badRequest().body(new MessageResponse("Incorrect register input or email is taken"));
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("U have no permissions to add new admin"));
            }
        } else if (registerRequest.isAdmin() && headerToken.isBlank()) {
            return ResponseEntity.badRequest().body(new MessageResponse("U have no permissions to add new admin"));
        }

        User user = userFacade.register(registerRequest.getFirstName(),
                registerRequest.getLastName(),
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getPictureUrl());
        return  user != null ? ResponseEntity.ok().body(new MessageResponse("Standard user has been registered"))
                             : ResponseEntity.badRequest().body(new MessageResponse("Incorrect register input or email is taken"));
    }
    @ApiOperation(value = "Login to the user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Login succeed"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 403, message = "U are not allowed to this resource"),
            @ApiResponse(code = 401, message = "U are not authorized")
    })
    @GetMapping("/login")
    public ResponseEntity<?> login(@ApiParam(name = "Login request", value = "Login request information")
                                       @Valid @RequestBody LoginRequest loginRequest) {
        String token = userFacade.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (Objects.equals(token, "INCORRECT_INPUT"))
            return ResponseEntity.badRequest().body(new MessageResponse("Incorrect login input"));
        User user = userFacade.getUserByToken(token);
        List<String> roles = user.getRoles().stream().map(Role::name).toList();
        return ResponseEntity.ok().body(new JwtResponse(
                token,
                user.getEmail(),
                roles
        ));
    }
    @ApiOperation(value = "Update the user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Update succeed"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 403, message = "U are not allowed to this resource"),
            @ApiResponse(code = 401, message = "U are not authorized")
    })
    @PutMapping("/update")
    public ResponseEntity<?> update(@ApiParam(name = "Update request",
            value = "Update request body needed to update user")
            @RequestBody SignupRequest updateRequest) {
        User user = userFacade.updateUser(updateRequest.getId(), updateRequest.getFirstName(),
                updateRequest.getLastName(), updateRequest.getEmail(),
                updateRequest.getPassword(), updateRequest.getPictureUrl(),
                updateRequest.isAdmin());
        return user == null ? ResponseEntity.badRequest().body(new MessageResponse("User data has not been modified"))
                            : ResponseEntity.ok().body(new MessageResponse("User data has been modified"));
    }
}

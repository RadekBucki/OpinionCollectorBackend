package pl.opinion_collector.backend.logic.user.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    private UserFacadeImpl userFacade;

    @GetMapping
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok().body(userFacade.getAllUsers());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignupRequest registerRequest, HttpServletRequest httpServletRequest) {
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
                        ? ResponseEntity.ok().body(new MessageResponse("ADMIN HAS BEEN REGISTERED"))
                        : ResponseEntity.badRequest().body(new MessageResponse("INCORRECT REGISTER INPUT OR EMAIL IS TAKEN"));
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("U HAVE NO PERMISSIONS TO ADD NEW ADMIN"));
            }
        } else if (registerRequest.isAdmin() && headerToken.isBlank()) {
            return ResponseEntity.badRequest().body(new MessageResponse("U HAVE NO PERMISSIONS TO ADD NEW ADMIN"));
        }

        User user = userFacade.register(registerRequest.getFirstName(),
                registerRequest.getLastName(),
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getPictureUrl());
        return  user != null ? ResponseEntity.ok().body(new MessageResponse("STANDARD USER HAS BEEN REGISTERED"))
                             : ResponseEntity.badRequest().body(new MessageResponse("INCORRECT REGISTER INPUT OR EMAIL IS TAKEN"));
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = userFacade.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (Objects.equals(token, "INCORRECT_INPUT"))
            return ResponseEntity.badRequest().body(new MessageResponse("INCORRECT LOGIN INPUT"));
        User user = userFacade.getUserByToken(token);
        List<String> roles = user.getRoles().stream().map(Role::name).toList();
        return ResponseEntity.ok().body(new JwtResponse(
                token,
                user.getEmail(),
                roles
        ));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody SignupRequest updateRequest) {
        User user = userFacade.updateUser(updateRequest.getId(), updateRequest.getFirstName(),
                updateRequest.getLastName(), updateRequest.getEmail(),
                updateRequest.getPassword(), updateRequest.getPictureUrl(),
                updateRequest.isAdmin());
        return user == null ? ResponseEntity.badRequest().body(new MessageResponse("USER DATA HAS NOT BEEN UPDATED"))
                            : ResponseEntity.ok().body(new MessageResponse("USER DATA HAS BEEN MODIFIED"));
    }
}

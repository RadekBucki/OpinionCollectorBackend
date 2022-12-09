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
        String headerToken = httpServletRequest.getHeader("Authorization").substring(7);
        if (registerRequest.getRole() != null) {
            if (registerRequest.getRole().contains(Role.ROLE_ADMIN.name()) && !headerToken.isBlank() &&
                    userFacade.getUserByToken(headerToken) != null) {
                    if (userFacade.getUserByToken(headerToken).getRoles().contains(Role.ROLE_ADMIN)) {
                        User user = userFacade.registerAdmin(registerRequest.getFirstName(),
                                registerRequest.getLastName(),
                                registerRequest.getEmail(),
                                registerRequest.getPassword(),
                                registerRequest.getPictureUrl());
                        return user != null
                                ? ResponseEntity.ok().body(new MessageResponse("ADMIN HAS BEEN REGISTERED"))
                                : ResponseEntity.badRequest().body(new MessageResponse("EMAIL IS TAKEN"));
                    } else {
                        return ResponseEntity.badRequest().body(new MessageResponse("U HAVE NO PERMISSIONS TO ADD NEW ADMIN"));
                    }
            } else {
                return ResponseEntity.badRequest().body(new MessageResponse("U HAVE NO PERMISSIONS TO ADD NEW ADMIN"));
            }
        }
        User user = userFacade.register(registerRequest.getFirstName(),
                registerRequest.getLastName(),
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getPictureUrl());
        return  user != null ? ResponseEntity.ok().body(new MessageResponse("STANDARD USER HAS BEEN REGISTERED"))
                             : ResponseEntity.badRequest().body(new MessageResponse("EMAIL IS TAKEN"));
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = userFacade.login(loginRequest.getUsername(), loginRequest.getPassword());
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
        boolean isAdmin = false;
        if (updateRequest.getRole() != null) {
            if (updateRequest.getRole().contains(Role.ROLE_ADMIN.name()))
                isAdmin = true;
        }
        User user = userFacade.updateUser(updateRequest.getId(), updateRequest.getFirstName(),
                updateRequest.getLastName(), updateRequest.getEmail(),
                updateRequest.getPassword(), updateRequest.getPictureUrl(),
                isAdmin);
        return user == null ? ResponseEntity.badRequest().body(new MessageResponse("USER DATA HAS NOT BEEN UPDATED"))
                            : ResponseEntity.ok().body(new MessageResponse("USER DATA HAS BEEN MODIFIED"));
    }
}

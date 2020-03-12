package com.home.ppmtool.web;

import com.home.ppmtool.Payload.JWTLoginSuccessResponse;
import com.home.ppmtool.Payload.LoginRequest;
import com.home.ppmtool.Security.JwtTokenProvider;
import com.home.ppmtool.domain.User;
import com.home.ppmtool.services.MapValidationErrorService;
import com.home.ppmtool.services.UserService;
import com.home.ppmtool.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.home.ppmtool.Security.SecurityConstraints.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private MapValidationErrorService mapValidationErrorService;
    @Autowired
    private UserService userService;
    @Autowired
    UserValidator userValidator;
    @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result){
        userValidator.validate(user,result);
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap!=null) return errorMap;
        User newUser = userService.saveUser(user);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap!=null) return  errorMap;
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTLoginSuccessResponse(true,jwt));

    }
}

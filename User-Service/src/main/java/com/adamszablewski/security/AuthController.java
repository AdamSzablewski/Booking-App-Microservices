package com.adamszablewski.security;



import com.adamszablewski.dto.RestResponseDTO;
import com.adamszablewski.security.dto.LoginDto;
import com.adamszablewski.security.dto.RegisterDto;
import com.adamszablewski.model.UserClass;
import com.adamszablewski.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/auth")
@Service
@AllArgsConstructor
public class AuthController {

   // private AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    private TokenGenerator tokenGenerator;
    private SecurityService securityService;




    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterDto registerDto){
        //System.out.println(registerDto);
        if (userRepository.existsByEmail(registerDto.getEmail())){
            return new ResponseEntity<>("username is taken", HttpStatus.BAD_REQUEST);
        }
        UserClass user = UserClass.builder()
                .phoneNumber(registerDto.getPhoneNumber())
                .email(registerDto.getEmail())
                .firstName(registerDto.getFirstName())
                .lastName(registerDto.getLastName())
                .country(registerDto.getCountry())
                .region(registerDto.getRegion())
                .city(registerDto.getCity())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .build();

        userRepository.save(user);

        //System.out.println(user.getPassword());
        return new ResponseEntity<>("User registered", HttpStatus.OK);
    }

//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginDto user){
//
//        securityService.validateUser(user);
//        String token = tokenGenerator.generateToken(user.getEmail());
//        return ResponseEntity.ok(token);
//    }
//    @PostMapping("/validate/token")
//    public ResponseEntity<RestResponseDTO<Boolean>> validateToken(@RequestBody String token){
//        String errorMessage = "";
//        boolean validated = false;
//        try {
//            validated = securityService.validateToken(token);
//        }catch (Exception e){
//            errorMessage = e.getMessage();
//        }
//        RestResponseDTO<Boolean> response = RestResponseDTO.<Boolean>builder()
//                .value(validated)
//                .error(errorMessage)
//                .build();
//
//        return ResponseEntity.ok(response);
//    }


}

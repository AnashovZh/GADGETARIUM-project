package zhanuzak.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zhanuzak.dto.request.SignInRequest;
import zhanuzak.dto.request.SignUpRequest;
import zhanuzak.dto.response.AuthenticationResponse;
import zhanuzak.enums.Role;
import zhanuzak.exceptions.exception.AlreadyExistException;
import zhanuzak.exceptions.exception.BadCreadentialException;
import zhanuzak.exceptions.exception.NotFoundException;
import zhanuzak.models.User;
import zhanuzak.repository.UserRepository;
import zhanuzak.security.jwt.JwtService;
import zhanuzak.service.AuthenticationService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponse signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.email())) {
            throw new AlreadyExistException("User with email:\" + signUpRequest.email() + \" already exists !");
        }
        User user = User.builder()
                .firstName(signUpRequest.firstName())
                .lastName(signUpRequest.lastName())
                .email(signUpRequest.email())
                .password(passwordEncoder.encode(signUpRequest.password()))
                .role(Role.USER)
                .createdDate(LocalDateTime.now())
                .build();
        log.info("Its working");
        String token = jwtService.generateToken(user);
        userRepository.save(user);
        return AuthenticationResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @Override
    public AuthenticationResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.getUserByEmail(signInRequest.getEmail()).orElseThrow(() ->
                new NotFoundException("User with email:" + signInRequest.getEmail() + " not found !!!"));
        if (signInRequest.getEmail().isBlank()) {
            throw new BadCreadentialException("Email is blank ");
        }
        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            throw new BadCreadentialException("Wrong password");
        }
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    @PostConstruct
    @Override
    public void init() {
        System.out.println("sout");
        User user = new User();
        user.setFirstName("Admin");
        user.setLastName("Adminbekov");
        user.setEmail("admin@gmail.com");
        user.setPassword(passwordEncoder.encode("admin123"));
        user.setRole(Role.ADMIN);
        user.setCreatedDate(LocalDateTime.now());
        if (!userRepository.existsByEmail("admin@gmail.com")) {
            userRepository.save(user);
        }
    }

    @PostConstruct
    @Override
    public void init2() {
        User user = new User();
        user.setFirstName("Zhanuzak");
        user.setLastName("Anashov");
        user.setEmail("zhanuzak@gmail.com");
        user.setPassword(passwordEncoder.encode("zhanuzak123"));
        user.setRole(Role.ADMIN);
        user.setCreatedDate(LocalDateTime.now());
        if (!userRepository.existsByEmail("zhanuzak@gmail.com")) {
            userRepository.save(user);
        }
    }
}

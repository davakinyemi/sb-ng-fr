package com.example.spring_boot_fr.auth;

import com.example.spring_boot_fr.email.EmailService;
import com.example.spring_boot_fr.role.RoleRepository;
import com.example.spring_boot_fr.security.JwtService;
import com.example.spring_boot_fr.user.User;
import com.example.spring_boot_fr.user.UserRepository;
import com.example.spring_boot_fr.user.token.Token;
import com.example.spring_boot_fr.user.token.TokenRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import static com.example.spring_boot_fr.email.EmailTemplateName.ACTIVATE_ACCOUNT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * @author Dave AKN
 * @version 1.0
 */
// @Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    public void register(RegistrationRequest request) throws MessagingException {
        var userRole = this.roleRepository.findByName("USER")
                // todo - better exception handling
                // .orElseThrow(() -> new IllegalStateException("ROLE USER not initialized"));
                .orElseThrow(() -> {
                    logger.error("ROLE USER not initialized.");
                    return new ResponseStatusException(
                            INTERNAL_SERVER_ERROR,
                            "Unable to register user."
                    );
                });

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(this.passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();

        this.userRepository.save(user);
        this.sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = this.generateAndSaveActivationToken(user);

        this.emailService.sendEmail(
                user.getEmail(),
                user.fullName(),
                ACTIVATE_ACCOUNT,
                this.activationUrl,
                newToken,
                "Account activation"
        );

    }

    private String generateAndSaveActivationToken(User user) {
        // todo - generate token
        int tokenLength = 6;
        String generatedToken = this.generateActivationCode(tokenLength);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        this.tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var auth = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("fullName", user.fullName());
        var jwtToken = this.jwtService.generateToken(claims, user);
        return AuthenticationResponse.builder()
                .token(jwtToken).build();
    }

    // @Transactional
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = this.tokenRepository.findByToken(token)
                // todo - exception has to be defined
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            this.sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token is expired. A new token has been sent to the same email address");
        }

        var user = this.userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        this.userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        this.tokenRepository.save(savedToken);
    }
}

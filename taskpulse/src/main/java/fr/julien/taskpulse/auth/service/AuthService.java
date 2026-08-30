package fr.julien.taskpulse.auth.service;

import java.time.Instant;
import java.util.LinkedHashSet;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.julien.taskpulse.auth.dto.AuthResult;
import fr.julien.taskpulse.auth.dto.LoginRequest;
import fr.julien.taskpulse.auth.dto.RegisterRequest;
import fr.julien.taskpulse.auth.dto.UserResponse;
import fr.julien.taskpulse.auth.exception.EmailAlreadyUsedException;
import fr.julien.taskpulse.auth.exception.UsernameAlreadyUsedException;
import fr.julien.taskpulse.auth.service.RefreshTokenService.RotatedToken;
import fr.julien.taskpulse.security.AppUserDetails;
import fr.julien.taskpulse.security.AppUserDetailsService;
import fr.julien.taskpulse.security.AuthProperties;
import fr.julien.taskpulse.user.entity.User;
import fr.julien.taskpulse.user.repository.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService userDetailsService;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;
    private final AuthProperties properties;

    public AuthService(UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            AppUserDetailsService userDetailsService,
            TokenService tokenService,
            RefreshTokenService refreshTokenService,
            AuthProperties properties) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
        this.refreshTokenService = refreshTokenService;
        this.properties = properties;
    }

    public AuthResult register(RegisterRequest request) {
        String email = request.email().trim().toLowerCase();
        String username = request.username().trim();

        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyUsedException();
        }
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyUsedException();
        }

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPasswordHash(passwordEncoder.encode(request.password()));
        user.setRoles(new LinkedHashSet<>(AppUserDetails.defaultRoles()));
        user.setCreatedAt(Instant.now().toString());

        User saved = userRepository.save(user);
        return issueTokens(new AppUserDetails(saved), UserResponse.fromEntity(saved));
    }

    public AuthResult login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email().trim().toLowerCase(), request.password()));

        AppUserDetails principal = (AppUserDetails) authentication.getPrincipal();
        return issueTokens(principal, currentUser(principal.getId()));
    }

    public AuthResult refresh(String rawRefreshToken) {
        RotatedToken rotated = refreshTokenService.rotate(rawRefreshToken);
        AppUserDetails principal = userDetailsService.loadUserById(rotated.userId());

        return new AuthResult(
                tokenService.generateAccessToken(principal),
                tokenService.accessTokenExpiresIn(),
                rotated.refreshToken(),
                properties.refreshTokenTtl(),
                currentUser(principal.getId()));
    }

    public void logout(String rawRefreshToken) {
        if (rawRefreshToken != null && !rawRefreshToken.isBlank()) {
            refreshTokenService.revoke(rawRefreshToken);
        }
    }

    public UserResponse currentUser(String userId) {
        return userRepository.findById(userId)
                .map(UserResponse::fromEntity)
                .orElseThrow(() -> new IllegalStateException("Utilisateur introuvable"));
    }

    private AuthResult issueTokens(AppUserDetails principal, UserResponse user) {
        return new AuthResult(
                tokenService.generateAccessToken(principal),
                tokenService.accessTokenExpiresIn(),
                refreshTokenService.issue(principal.getId()),
                properties.refreshTokenTtl(),
                user);
    }
}

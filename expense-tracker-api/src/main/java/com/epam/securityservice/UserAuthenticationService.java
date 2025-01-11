package com.epam.securityservice;

import com.epam.dto.LoginUserDto;
import com.epam.dto.RegisterUserDto;
import com.epam.model.AppUser;
import com.epam.repository.AppUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserAuthenticationService {
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public UserAuthenticationService(AppUserRepository appUserRepository, BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public AppUser register(RegisterUserDto registerUserDto) {
        AppUser appUser = AppUser.builder()
                .fullName(registerUserDto.getFullName())
                .email(registerUserDto.getEmail())
                .password(passwordEncoder.encode(registerUserDto.getPassword()))
                .build();

        return appUserRepository.save(appUser);
    }

    public AppUser login(LoginUserDto loginUserDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(), loginUserDto.getPassword()));

        return appUserRepository.findByEmail(loginUserDto.getEmail()).orElseThrow(() -> new UsernameNotFoundException(loginUserDto.getEmail()));
    }

}

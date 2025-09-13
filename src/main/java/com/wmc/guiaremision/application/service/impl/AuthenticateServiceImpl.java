package com.wmc.guiaremision.application.service.impl;

import com.wmc.guiaremision.application.dto.ServiceResponse;
import com.wmc.guiaremision.application.service.AuthenticateService;
import com.wmc.guiaremision.domain.entity.RolEntity;
import com.wmc.guiaremision.domain.entity.UserEntity;
import com.wmc.guiaremision.domain.repository.RolRepository;
import com.wmc.guiaremision.domain.repository.UserRepository;
import com.wmc.guiaremision.domain.spi.security.TokenProvider;
import com.wmc.guiaremision.domain.spi.security.dto.JwtToken;
import com.wmc.guiaremision.infrastructure.web.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticateServiceImpl implements AuthenticateService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenProvider tokenProvider;
  private final RolRepository rolRepository;

  @Override
  @Transactional
  public ServiceResponse authenticate(String username, String password) {
    log.info("Autenticando usuario: {}", username);

    UserEntity user = this.userRepository.findByUserName(username)
        .orElseThrow(() -> new BadRequestException("Invalido username o password"));

    if (!this.passwordEncoder.matches(password, user.getPassword())) {
      throw new BadRequestException("Invalido username o password");
    }

    if (user.getStatus().equals("I")) {
      throw new BadRequestException("Usuario inactivo, contacte con el administrador");
    }

    List<RolEntity> roles = this.rolRepository.findByUserId(user.getUserId());

    JwtToken jwtToken = this.tokenProvider.generateToken(user, roles);

    log.info("Token generado exitosamente para usuario: {}", username);

    return ServiceResponse.builder()
        .requestId(jwtToken.getToken())
        .data(jwtToken)
        .success(true)
        .build();
  }
}

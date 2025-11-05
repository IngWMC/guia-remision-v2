package com.wmc.guiaremision.application.service;

import com.wmc.guiaremision.domain.spi.security.dto.JwtToken;

public interface AuthService {
    JwtToken authenticate(String username, String password);
}

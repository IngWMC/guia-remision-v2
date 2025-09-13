package com.wmc.guiaremision.application.service;

import com.wmc.guiaremision.application.dto.ServiceResponse;

public interface AuthenticateService {
    ServiceResponse authenticate(String username, String password);
}

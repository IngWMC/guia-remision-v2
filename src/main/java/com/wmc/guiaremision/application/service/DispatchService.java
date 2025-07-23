package com.wmc.guiaremision.application.service;

import com.wmc.guiaremision.application.dto.response.ServiceResponse;
import com.wmc.guiaremision.domain.model.Dispatch;

public interface DispatchService {
    ServiceResponse generateDispatch(Dispatch document);
}

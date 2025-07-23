package com.wmc.guiaremision.domain.service;

import com.wmc.guiaremision.domain.model.Dispatch;

public interface SignatureService {
    Dispatch signXml(Dispatch dispatch);
} 
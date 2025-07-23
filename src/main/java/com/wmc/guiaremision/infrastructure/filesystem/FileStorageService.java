package com.wmc.guiaremision.infrastructure.filesystem;

import com.wmc.guiaremision.domain.model.Dispatch;

public interface FileStorageService {
    Dispatch saveXml(Dispatch dispatch);
    Dispatch savePdf(Dispatch dispatch);
    Dispatch saveCdr(Dispatch dispatch);
} 
package com.wmc.guiaremision.domain.spi.file;

import com.wmc.guiaremision.domain.model.Dispatch;

public interface FileStoragePort {
    Dispatch saveXml(Dispatch dispatch);
    Dispatch savePdf(Dispatch dispatch);
    Dispatch saveCdr(Dispatch dispatch);
} 
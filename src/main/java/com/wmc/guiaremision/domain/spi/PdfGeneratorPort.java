package com.wmc.guiaremision.domain.spi;

import com.wmc.guiaremision.domain.model.Dispatch;

public interface PdfGeneratorPort {
    Dispatch generatePdf(Dispatch dispatch);
} 
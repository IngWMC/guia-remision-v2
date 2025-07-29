package com.wmc.guiaremision.infrastructure.filesystem;

import com.wmc.guiaremision.domain.model.Dispatch;
import com.wmc.guiaremision.domain.spi.file.PdfGeneratorPort;
import org.springframework.stereotype.Service;

@Service
public class PdfGeneratorPortImpl implements PdfGeneratorPort {

    @Override
    public Dispatch generatePdf(Dispatch dispatch) {
        return null;
    }
} 
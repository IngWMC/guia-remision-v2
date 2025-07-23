package com.wmc.guiaremision.domain.repository;

import com.wmc.guiaremision.domain.model.Dispatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DispatchRepository extends JpaRepository<Dispatch, String> {
    // Métodos personalizados si se requieren
} 
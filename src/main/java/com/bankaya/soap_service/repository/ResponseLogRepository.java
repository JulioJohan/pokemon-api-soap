package com.bankaya.soap_service.repository;

import com.bankaya.soap_service.model.entity.ResponseLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseLogRepository extends JpaRepository<ResponseLogEntity,Long> {
}

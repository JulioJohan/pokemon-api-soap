package com.bankaya.soap_service.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "response_log")
public class ResponseLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_response_log", nullable = false, unique = true)
    private Long id;

    @Column(name = "origin_ip", nullable = false, length = 100)
    private String originIp;

    @Column(name = "request_date",nullable = false)
    private LocalDateTime requestDate;

    @Column(name = "request_duration", nullable = false)
    private Long requestDuration;

    @Column(name = "executed_method", nullable = false)
    private String executedMethod;

    @Column(name = "request",nullable = true)
    private String request;

    @Column(name = "response",nullable = true,length = 100000)
    private String response;

}

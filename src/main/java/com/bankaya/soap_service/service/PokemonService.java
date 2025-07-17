package com.bankaya.soap_service.service;


import com.bankaya.soap_service.exception.NotFoundException;
import com.bankaya.soap_service.feign.PokemonFeignClient;
import com.bankaya.soap_service.model.PokemonResponse;
import com.bankaya.soap_service.model.entity.ResponseLogEntity;
import com.bankaya.soap_service.repository.ResponseLogRepository;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PokemonService {

    private final ResponseLogRepository responseLogRepository;
    private final PokemonFeignClient pokemonFeignClient;

    @Retry(name = "pokemonApi")
    @Cacheable(value = "pokemonCache", key = "#name", unless = "#result == null")
    public PokemonResponse getInfoPokemon(String name,String methodEndpoint,String clientIp){
        log.info("Fetching Pokemon data for {}:", name);
        ResponseLogEntity responseLogEntity = ResponseLogEntity
                .builder()
                .originIp(clientIp)
                .requestDate(LocalDateTime.now())
                .executedMethod(methodEndpoint)
                .request(name)
                .build();

        long startTime = System.nanoTime();
        Optional<PokemonResponse> response = pokemonFeignClient.getInfoPokemon(name.toLowerCase());
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000;
        responseLogEntity.setResponse(response.map(PokemonResponse::toString).orElse("No data found"));
        responseLogEntity.setRequestDuration(duration);
        log.info("Response {}",responseLogEntity);
        responseLogRepository.save(responseLogEntity);
        return response.orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND, "Pokemon not found"));
    }
}

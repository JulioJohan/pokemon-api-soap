package com.bankaya.soap_service.feign;


import com.bankaya.soap_service.config.FeignErrorClientConfiguration;
import com.bankaya.soap_service.model.PokemonResponse;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
        value = "pokemon-client",
        url = "${pokemon.api.url}",
        configuration = FeignErrorClientConfiguration.class
)
public interface PokemonFeignClient {

    @GetMapping(value = "/pokemon/{name}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    Optional<PokemonResponse> getInfoPokemon(@PathVariable String name);
}

package com.bankaya.soap_service.service;


import com.bankaya.soap_service.model.PokemonResponse;
import com.bankaya.soap_service.model.entity.ResponseLogEntity;
import com.bankaya.soap_service.repository.ResponseLogRepository;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@AutoConfigureEmbeddedDatabase(
        provider = AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY,
        refresh = AutoConfigureEmbeddedDatabase.RefreshMode.AFTER_EACH_TEST_METHOD,
        type = AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES
)
@EnableJpaRepositories(basePackageClasses = ResponseLogRepository.class)
@EntityScan(basePackageClasses = ResponseLogEntity.class)
@AutoConfigureWireMock(port = 8181)
@Sql(scripts = "classpath:init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class PokemonServiceTest {

    @Autowired
    private PokemonService pokemonService;

    @Test
    void getInfoPokemonTest(){

        PokemonResponse pokemonResponse = pokemonService.getInfoPokemon("pikachu", "getInfoPokemon", "");
        Assertions.assertNotNull(pokemonResponse);
    }

}

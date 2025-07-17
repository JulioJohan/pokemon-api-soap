package com.bankaya.soap_service.endpoint;


import com.bankaya.soap_service.MockHttpServletRequestConfigTest;
import com.bankaya.soap_service.config.WebServiceConfig;
import com.bankaya.soap_service.feign.PokemonFeignClient;
import com.bankaya.soap_service.model.PokemonResponse;
import com.bankaya.soap_service.model.pokemonresponse.Ability;
import com.bankaya.soap_service.model.pokemonresponse.AbilityDetail;
import com.bankaya.soap_service.model.pokemonresponse.Item;
import com.bankaya.soap_service.model.pokemonresponse.ItemDetail;
import com.bankaya.soap_service.repository.ResponseLogRepository;
import com.bankaya.soap_service.service.PokemonService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.webservices.server.AutoConfigureMockWebServiceClient;
import org.springframework.boot.test.autoconfigure.webservices.server.MockWebServiceClientAutoConfiguration;
import org.springframework.boot.test.autoconfigure.webservices.server.WebServiceServerTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.server.MockWebSession;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

import javax.xml.transform.Source;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.*;


@Import({WebServiceConfig.class,MockHttpServletRequestConfigTest.class})
@WebServiceServerTest(PokemonEndpoint.class)
class PokemonEndpointTest {


    @Autowired
    private MockWebServiceClient client;

    @MockitoBean
    private PokemonService pokemonService;


    PokemonResponse pokemonResponse;

    List<Ability> abilities;

    List<Item> heldItems;

    @BeforeEach
    void setUp() {
        abilities = new ArrayList<>();
        heldItems = new ArrayList<>();
        pokemonResponse = new PokemonResponse();
        pokemonResponse.setName("ditto");
        pokemonResponse.setId(132);
        pokemonResponse.setBaseExperience(101);
        pokemonResponse.setLocationAreaEncounters("https://pokeapi.co/api/v2/pokemon/132/encounters");


        Ability abilityTwo = new Ability();
        AbilityDetail abilityDetailTwo = new AbilityDetail();
        abilityDetailTwo.setName("imposter");
        abilityDetailTwo.setUrl("https://pokeapi.co/api/v2/ability/150/");
        abilityTwo.setAbility(abilityDetailTwo);

        Ability ability = new Ability();
        AbilityDetail abilityDetail = new AbilityDetail();
        abilityDetail.setName("limber");
        abilityDetail.setUrl("https://pokeapi.co/api/v2/ability/7/");

        ability.setAbility(abilityDetail);
        abilities.add(ability);
        abilities.add(abilityTwo);
        pokemonResponse.setAbilities(abilities);


        Item item = new Item();
        ItemDetail itemDetail = new ItemDetail();
        itemDetail.setName("metal-powder");
        itemDetail.setUrl("https://pokeapi.co/api/v2/item/234/");

        Item item2 = new Item();
        ItemDetail itemDetail2 = new ItemDetail();
        itemDetail.setName("quick-powder");
        itemDetail.setUrl("https://pokeapi.co/api/v2/item/251/");


        item.setItem(itemDetail);
        item2.setItem(itemDetail2);
        heldItems.add(item);
        heldItems.add(item2);
        pokemonResponse.setHeldItems(heldItems);
    }


    @Test
    void getPokemonAbilitiesTest(){
        StringSource requestPayload = new StringSource(
                "<bd:getPokemonAbilitiesRequest xmlns:bd='http://bankaya/pokemon/api/v1'>" +
                "<bd:name>ditto</bd:name>" +
                "</bd:getPokemonAbilitiesRequest>"
        );

        StringSource responsePayload = new StringSource(
                "<bd:getPokemonAbilitiesResponse xmlns:bd='http://bankaya/pokemon/api/v1'>" +
                        "<bd:abilities><bd:name>limber</bd:name><bd:url>https://pokeapi.co/api/v2/ability/7/</bd:url></bd:abilities>" +
                        "<bd:abilities><bd:name>imposter</bd:name><bd:url>https://pokeapi.co/api/v2/ability/150/</bd:url></bd:abilities>" +
                        "</bd:getPokemonAbilitiesResponse>"
        );
        when(pokemonService.getInfoPokemon("ditto", "getPokemonAbilities", null))
                .thenReturn(pokemonResponse);

        client.sendRequest(withPayload(requestPayload))
                .andExpect(payload(responsePayload));
    }
    @Test
    void getPokemonBaseExperienceTest(){
        StringSource requestPayload = new StringSource(
                "<bd:getPokemonBaseExperienceRequest xmlns:bd='http://bankaya/pokemon/api/v1'>" +
                        "<bd:name>ditto</bd:name>" +
                        "</bd:getPokemonBaseExperienceRequest>"
        );

        StringSource responsePayload = new StringSource(
                "<bd:getPokemonBaseExperienceResponse xmlns:bd='http://bankaya/pokemon/api/v1'>" +
                        "<bd:baseExperience>101</bd:baseExperience>" +
                        "</bd:getPokemonBaseExperienceResponse>"
        );
        when(pokemonService.getInfoPokemon("ditto", "getPokemonBaseExperience", null))
                .thenReturn(pokemonResponse);
        client.sendRequest(withPayload(requestPayload))
                .andExpect(payload(responsePayload));
    }


    @Test
    void getPokemonHeldItemsTest(){
        StringSource requestPayload = new StringSource(
                "<bd:getPokemonHeldItemsRequest xmlns:bd='http://bankaya/pokemon/api/v1'>" +
                        "<bd:name>ditto</bd:name>" +
                        "</bd:getPokemonHeldItemsRequest>"
        );
        when(pokemonService.getInfoPokemon("ditto", "getPokemonHeldItems", null))
                .thenReturn(pokemonResponse);
        client.sendRequest(withPayload(requestPayload))
                .andExpect(noFault());
    }

    @Test
    void getPokemonIdTest(){
        StringSource requestPayload = new StringSource(
                "<bd:getPokemonIdRequest xmlns:bd='http://bankaya/pokemon/api/v1'>" +
                        "<bd:name>ditto</bd:name>" +
                        "</bd:getPokemonIdRequest>"
        );

        StringSource responsePayload = new StringSource(
                "<bd:getPokemonIdResponse xmlns:bd='http://bankaya/pokemon/api/v1'>" +
                        "<bd:id>132</bd:id>" +
                        "</bd:getPokemonIdResponse>"
        );
        when(pokemonService.getInfoPokemon("ditto", "getPokemonId", null))
                .thenReturn(pokemonResponse);
        client.sendRequest(withPayload(requestPayload))
                .andExpect(payload(responsePayload));
    }


    @Test
    void getPokemonNameTest(){
        StringSource requestPayload = new StringSource(
                "<bd:getPokemonNameRequest xmlns:bd='http://bankaya/pokemon/api/v1'>" +
                        "<bd:name>ditto</bd:name>" +
                        "</bd:getPokemonNameRequest>"
        );

        StringSource responsePayload = new StringSource(
                "<bd:getPokemonNameResponse xmlns:bd='http://bankaya/pokemon/api/v1'>" +
                        "<bd:name>ditto</bd:name>" +
                        "</bd:getPokemonNameResponse>"
        );
        when(pokemonService.getInfoPokemon("ditto", "getPokemonName", null))
                .thenReturn(pokemonResponse);
        client.sendRequest(withPayload(requestPayload))
                .andExpect(payload(responsePayload));
    }


    @Test
    void getPokemonLocationAreaResponseTest(){
        StringSource requestPayload = new StringSource(
                "<bd:getPokemonLocationAreaRequest xmlns:bd='http://bankaya/pokemon/api/v1'>" +
                        "<bd:name>ditto</bd:name>" +
                        "</bd:getPokemonLocationAreaRequest>"
        );

        StringSource responsePayload = new StringSource(
                "<bd:getPokemonLocationAreaResponse xmlns:bd='http://bankaya/pokemon/api/v1'>" +
                        "<bd:locationAreaEncounters>https://pokeapi.co/api/v2/pokemon/132/encounters</bd:locationAreaEncounters>" +
                        "</bd:getPokemonLocationAreaResponse>"
        );
        when(pokemonService.getInfoPokemon("ditto", "getPokemonLocationAreaEncounters", null))
                .thenReturn(pokemonResponse);
        client.sendRequest(withPayload(requestPayload))
                .andExpect(payload(responsePayload));
    }



}

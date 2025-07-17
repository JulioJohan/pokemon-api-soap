package com.bankaya.soap_service.endpoint;


import com.bankaya.soap_service.model.PokemonResponse;
import com.bankaya.soap_service.service.PokemonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.bankaya.soap.*;



@Slf4j
@Endpoint
@RequiredArgsConstructor
public class PokemonEndpoint {
    private static final String NAMESPACE_URI = "http://bankaya/pokemon/api/v1";
    private final PokemonService pokemonService;
    private final HttpServletRequest httpServletRequest;


    @Operation(summary = "Get Pokemon by name and return abilities" ,
            description = "This endpoint retrieves the abilities of a Pokemon by its name. " +
            "It returns a list of abilities with their names and URLs.",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/xml",
                            schema = @Schema(
                                    implementation = GetPokemonAbilitiesRequest.class
                            ),
                            examples = {
                                    @ExampleObject(
                                            name = "Request Abilities ",
                                            summary = "Example of a request to get Pokemon abilities",
                                            value = """
                                                    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                                                                      xmlns:gs="http://bankaya/pokemon/api/v1">
                                                        <soapenv:Header/>
                                                        <soapenv:Body>
                                                            <gs:getPokemonAbilitiesRequest>
                                                                <gs:name>pikachu</gs:name>
                                                            </gs:getPokemonAbilitiesRequest>
                                                        </soapenv:Body>
                                                    </soapenv:Envelope>
                                                    
                                                    """
                                    )
                            }
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved Pokemon abilities",
                            content = @Content(
                                    mediaType = "application/xml",
                                    schema = @Schema(implementation = GetPokemonAbilitiesResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Pokemon not found",
                            content = @Content(
                                    mediaType = "application/xml",
                                    examples = {
                                            @ExampleObject(
                                                    name = "Pokemon Not Found",
                                                    summary = "Example of a response when the Pokemon is not found",
                                                    value = """
                                                            <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
                                                               <SOAP-ENV:Header />
                                                               <SOAP-ENV:Body>
                                                                 <SOAP-ENV:Fault>
                                                                   <faultcode>SOAP-ENV:Server</faultcode>
                                                                   <faultstring xml:lang="en">[404 Not Found] during [GET] to [https://pokeapi.co/api/v2/pokemon/pidawdkachu] [PokemonFeignClient#getInfoPokemon(String)]: [Not Found]</faultstring>
                                                                 </SOAP-ENV:Fault>
                                                               </SOAP-ENV:Body>
                                                            </SOAP-ENV:Envelope>
                                                            """
                                            )
                                    }
                            )
                    )
            }

    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Pokemon abilities"),
            @ApiResponse(responseCode = "404", description = "Pokemon not found"),
    })
    @PayloadRoot(namespace = NAMESPACE_URI,localPart = "getPokemonAbilitiesRequest")
    @ResponsePayload
    public GetPokemonAbilitiesResponse getPokemonAbilities(@RequestPayload GetPokemonAbilitiesRequest request){
        PokemonResponse pokemonResponse = pokemonService
                .getInfoPokemon(request.getName(), "getPokemonAbilities",httpServletRequest.getRemoteAddr());

        log.info("Fetching abilities for Pokemon: {}", pokemonResponse);
        GetPokemonAbilitiesResponse response = new GetPokemonAbilitiesResponse();
        pokemonResponse.getAbilities().forEach(ability -> {
            AbilitiesDetail abilities = new AbilitiesDetail();
            abilities.setName(ability.getAbility().getName());
            abilities.setUrl(ability.getAbility().getUrl());
            response.getAbilities().add(abilities);
        });
        return response;
    }




    @Operation(summary = "Get Pokemon by name and return experience" ,
            description = "This endpoint retrieves the base experience of a Pokemon by its name. ",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/xml",
                            schema = @Schema(
                                    implementation = GetPokemonBaseExperienceRequest.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved Pokemon experience",
                            content = @Content(
                                    mediaType = "application/xml",
                                    schema = @Schema(implementation = GetPokemonBaseExperienceResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Pokemon not found",
                            content = @Content(
                                    mediaType = "application/xml",
                                    examples = {
                                            @ExampleObject(
                                                    name = "Pokemon Not Found",
                                                    summary = "Example of a response when the Pokemon is not found",
                                                    value = """
                                                            <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
                                                               <SOAP-ENV:Header />
                                                               <SOAP-ENV:Body>
                                                                 <SOAP-ENV:Fault>
                                                                   <faultcode>SOAP-ENV:Server</faultcode>
                                                                   <faultstring xml:lang="en">[404 Not Found]\s
                                                                   during [GET] to [https://pokeapi.co/api/v2/pokemon/pidawdkachu]\s
                                                                   [PokemonFeignClient#getInfoPokemon(String)]:\s
                                                                   [Not Found]</faultstring>
                                                                 </SOAP-ENV:Fault>
                                                               </SOAP-ENV:Body>
                                                            </SOAP-ENV:Envelope>
                                                           \s"""
                                            )
                                    }
                            )
                    )
            }

    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Pokemon experience"),
            @ApiResponse(responseCode = "404", description = "Pokemon not found"),
    })
    @PayloadRoot(namespace = NAMESPACE_URI,localPart = "getPokemonBaseExperienceRequest")
    @ResponsePayload
    public GetPokemonBaseExperienceResponse getPokemonBaseExperience(@RequestPayload GetPokemonBaseExperienceRequest request){
        PokemonResponse pokemonResponse = pokemonService
                .getInfoPokemon(request.getName(),"getPokemonBaseExperience", httpServletRequest.getRemoteAddr());
        GetPokemonBaseExperienceResponse response = new GetPokemonBaseExperienceResponse();
        response.setBaseExperience(pokemonResponse.getBaseExperience());
        return response;
    }

    @Operation(summary = "Get Pokemon by name and return held items" ,
            description = "This endpoint retrieves the held items of a Pokemon by its name. ",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/xml",
                            schema = @Schema(
                                    implementation = GetPokemonHeldItemsRequest.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved Pokemon held items",
                            content = @Content(
                                    mediaType = "application/xml",
                                    schema = @Schema(implementation = GetPokemonHeldItemsResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Pokemon not found",
                            content = @Content(
                                    mediaType = "application/xml",
                                    examples = {
                                            @ExampleObject(
                                                    name = "Pokemon Not Found",
                                                    summary = "Example of a response when the Pokemon is not found",
                                                    value = """
                                                            <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
                                                               <SOAP-ENV:Header />
                                                               <SOAP-ENV:Body>
                                                                 <SOAP-ENV:Fault>
                                                                   <faultcode>SOAP-ENV:Server</faultcode>
                                                                   <faultstring xml:lang="en">[404 Not Found]\s
                                                                   during [GET] to [https://pokeapi.co/api/v2/pokemon/pidawdkachu]\s
                                                                   [PokemonFeignClient#getInfoPokemon(String)]:\s
                                                                   [Not Found]</faultstring>
                                                                 </SOAP-ENV:Fault>
                                                               </SOAP-ENV:Body>
                                                            </SOAP-ENV:Envelope>
                                                           \s"""
                                            )
                                    }
                            )
                    )
            }

    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Pokemon held items"),
            @ApiResponse(responseCode = "404", description = "Pokemon not found"),
    })
    @PayloadRoot(namespace = NAMESPACE_URI,localPart = "getPokemonHeldItemsRequest")
    @ResponsePayload
    public GetPokemonHeldItemsResponse getPokemonHeldItems(@RequestPayload GetPokemonHeldItemsRequest request){
        PokemonResponse pokemonResponse = pokemonService
                .getInfoPokemon(request.getName(),"getPokemonHeldItems",httpServletRequest.getRemoteAddr());
        GetPokemonHeldItemsResponse response = new GetPokemonHeldItemsResponse();
        pokemonResponse.getHeldItems().forEach(heldItem -> {
            Items item = new Items();
            item.setName(heldItem.getItem().getName());
            item.setUrl(heldItem.getItem().getUrl());
            response.getHeldItems().add(item);
        });
        return response;
    }


    @Operation(summary = "Get Pokemon by name and return pokemon ID" ,
            description = "This endpoint retrieves the id of a Pokemon by its name. ",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/xml",
                            schema = @Schema(
                                    implementation = GetPokemonIdRequest.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved Pokemon id",
                            content = @Content(
                                    mediaType = "application/xml",
                                    schema = @Schema(implementation = GetPokemonIdResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Pokemon not found",
                            content = @Content(
                                    mediaType = "application/xml",
                                    examples = {
                                            @ExampleObject(
                                                    name = "Pokemon Not Found",
                                                    summary = "Example of a response when the Pokemon is not found",
                                                    value = """
                                                            <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
                                                               <SOAP-ENV:Header />
                                                               <SOAP-ENV:Body>
                                                                 <SOAP-ENV:Fault>
                                                                   <faultcode>SOAP-ENV:Server</faultcode>
                                                                   <faultstring xml:lang="en">[404 Not Found]\s
                                                                   during [GET] to [https://pokeapi.co/api/v2/pokemon/pidawdkachu]\s
                                                                   [PokemonFeignClient#getInfoPokemon(String)]:\s
                                                                   [Not Found]</faultstring>
                                                                 </SOAP-ENV:Fault>
                                                               </SOAP-ENV:Body>
                                                            </SOAP-ENV:Envelope>
                                                           \s"""
                                            )
                                    }
                            )
                    )
            }

    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Pokemon id"),
            @ApiResponse(responseCode = "404", description = "Pokemon not found"),
    })
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPokemonIdRequest")
    @ResponsePayload
    public GetPokemonIdResponse getPokemonId(@RequestPayload GetPokemonIdRequest request){
        PokemonResponse pokemonResponse = pokemonService
                .getInfoPokemon(request.getName(), "getPokemonId",httpServletRequest.getRemoteAddr());
        GetPokemonIdResponse response = new GetPokemonIdResponse();
        response.setId(pokemonResponse.getId());
        return response;
    }

    @Operation(summary = "Get Pokemon by name and return pokemon name" ,
            description = "This endpoint retrieves the name of a Pokemon by its name. ",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/xml",
                            schema = @Schema(
                                    implementation = GetPokemonNameRequest.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved Pokemon name",
                            content = @Content(
                                    mediaType = "application/xml",
                                    schema = @Schema(implementation = GetPokemonNameResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Pokemon not found",
                            content = @Content(
                                    mediaType = "application/xml",
                                    examples = {
                                            @ExampleObject(
                                                    name = "Pokemon Not Found",
                                                    summary = "Example of a response when the Pokemon is not found",
                                                    value = """
                                                            <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
                                                               <SOAP-ENV:Header />
                                                               <SOAP-ENV:Body>
                                                                 <SOAP-ENV:Fault>
                                                                   <faultcode>SOAP-ENV:Server</faultcode>
                                                                   <faultstring xml:lang="en">[404 Not Found]\s
                                                                   during [GET] to [https://pokeapi.co/api/v2/pokemon/pidawdkachu]\s
                                                                   [PokemonFeignClient#getInfoPokemon(String)]:\s
                                                                   [Not Found]</faultstring>
                                                                 </SOAP-ENV:Fault>
                                                               </SOAP-ENV:Body>
                                                            </SOAP-ENV:Envelope>
                                                           \s"""
                                            )
                                    }
                            )
                    )
            }

    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Pokemon name"),
            @ApiResponse(responseCode = "404", description = "Pokemon not found"),
    })
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPokemonNameRequest")
    @ResponsePayload
    public GetPokemonNameResponse getPokemonName(@RequestPayload GetPokemonNameRequest request){
        PokemonResponse pokemonResponse = pokemonService
                .getInfoPokemon(request.getName(),"getPokemonName", httpServletRequest.getRemoteAddr());
        GetPokemonNameResponse response = new GetPokemonNameResponse();
        response.setName(pokemonResponse.getName());
        return response;
    }


    @Operation(summary = "Get Pokemon by name and return pokemon location area encounters" ,
            description = "This endpoint retrieves the location area encounters of a Pokemon by its name. ",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/xml",
                            schema = @Schema(
                                    implementation = GetPokemonLocationAreaRequest.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successfully retrieved Pokemon location area encounters",
                            content = @Content(
                                    mediaType = "application/xml",
                                    schema = @Schema(implementation = GetPokemonLocationAreaResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Pokemon not found",
                            content = @Content(
                                    mediaType = "application/xml",
                                    examples = {
                                            @ExampleObject(
                                                    name = "Pokemon Not Found",
                                                    summary = "Example of a response when the Pokemon is not found",
                                                    value = """
                                                            <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
                                                               <SOAP-ENV:Header />
                                                               <SOAP-ENV:Body>
                                                                 <SOAP-ENV:Fault>
                                                                   <faultcode>SOAP-ENV:Server</faultcode>
                                                                   <faultstring xml:lang="en">[404 Not Found]\s
                                                                   during [GET] to [https://pokeapi.co/api/v2/pokemon/pidawdkachu]\s
                                                                   [PokemonFeignClient#getInfoPokemon(String)]:\s
                                                                   [Not Found]</faultstring>
                                                                 </SOAP-ENV:Fault>
                                                               </SOAP-ENV:Body>
                                                            </SOAP-ENV:Envelope>
                                                           \s"""
                                            )
                                    }
                            )
                    )
            }

    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved Pokemon name"),
            @ApiResponse(responseCode = "404", description = "Pokemon not found"),
    })
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPokemonLocationAreaRequest")
    @ResponsePayload
    public GetPokemonLocationAreaResponse getPokemonLocationAreaEncounters(@RequestPayload GetPokemonLocationAreaRequest request){
        PokemonResponse pokemonResponse = pokemonService
                .getInfoPokemon(request.getName(), "getPokemonLocationAreaEncounters",httpServletRequest.getRemoteAddr());
        GetPokemonLocationAreaResponse response = new GetPokemonLocationAreaResponse();
        response.setLocationAreaEncounters(pokemonResponse.getLocationAreaEncounters());
        return response;
    }


}

package com.bankaya.soap_service.model.pokemonresponse;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbilityDetail {

    private String name;

    private String url;
}

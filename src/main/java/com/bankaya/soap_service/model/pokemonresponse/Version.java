package com.bankaya.soap_service.model.pokemonresponse;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Version {

    private String rarity;

    private VersionDetail version;


}

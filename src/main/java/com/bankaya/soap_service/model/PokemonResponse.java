package com.bankaya.soap_service.model;


import com.bankaya.soap_service.model.pokemonresponse.Ability;
import com.bankaya.soap_service.model.pokemonresponse.Item;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PokemonResponse {

    private int id;

    private String name;

    private List<Ability> abilities;

    @JsonProperty(value = "base_experience")
    private int baseExperience;

    @JsonProperty(value = "location_area_encounters")
    private String locationAreaEncounters;

    @JsonProperty(value = "held_items")
    private List<Item> heldItems;





}

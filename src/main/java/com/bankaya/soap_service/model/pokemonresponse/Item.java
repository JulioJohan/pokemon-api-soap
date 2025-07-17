package com.bankaya.soap_service.model.pokemonresponse;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    private ItemDetail item;

    @JsonProperty(value = "version_details")
    private List<Version> versionDetails;

}

package com.project.dtos.kursnalista;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KursnaListaRequestDto {

    @NotBlank(message = "osnovna valuta je obavezna")
    private String osnovnaValuta;

    @NotBlank(message = "Naziv je obavezan")
    private String naziv;
}

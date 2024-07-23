package com.project.serviceinterfaces;

import com.project.dtos.zahtev.PostZahtevDto;
import com.project.dtos.zahtev.ZahtevDto;

import java.util.List;

public interface ZahtevService {
    List<ZahtevDto> findAll();

    List<ZahtevDto> findByClientsEmail(String email);

    PostZahtevDto create(PostZahtevDto postZahtevDto, String email);

    ZahtevDto decide(String brojRacuna, String decision);
}

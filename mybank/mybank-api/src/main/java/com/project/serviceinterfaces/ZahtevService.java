package com.project.serviceinterfaces;

import com.project.dtos.zahtev.ZahtevDto;

import java.util.List;

public interface ZahtevService {
    List<ZahtevDto> findAll();

    List<ZahtevDto> findMyByEmail(String email);


}

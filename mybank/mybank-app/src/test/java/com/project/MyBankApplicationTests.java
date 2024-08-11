package com.project;


import com.project.domain.repositoryinterfaces.AdresaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
//@ExtendWith(MockitoExtension.class)
class MyBankApplicationTests {

	@Test
	void contextLoads() {
	}

	@Mock
	private AdresaRepository adresaRepository;
//
//	@Mock
//	private ModelMapper modelMapper;
//
//	@InjectMocks
//	private AdresaServiceImpl adresaServiceImpl;
//
//	private Adresa adresa;
//	private AdresaResponseDto adresaResponseDto;
//	private AdresaRequestDto adresaRequestDto;
//
//	@BeforeEach
//	void setUp() {
//		adresa = new Adresa();
//		adresaResponseDto = new AdresaResponseDto();
//		adresaRequestDto = new AdresaRequestDto();
//	}
//
//	@Test
//	void testFindAll() {
//		List<Adresa> adrese = Arrays.asList(adresa);
//		List<AdresaResponseDto> adreseDto = Arrays.asList(adresaResponseDto);
//
//		when(adresaRepository.findAll()).thenReturn(adrese);
//		when(modelMapper.map(adrese, new TypeToken<List<AdresaResponseDto>>() {}.getType())).thenReturn(adreseDto);
//
//		List<AdresaResponseDto> result = adresaServiceImpl.findAll();
//
//		assertEquals(adreseDto, result);
//		verify(adresaRepository).findAll();
//		verify(modelMapper).map(adrese, new TypeToken<List<AdresaResponseDto>>() {}.getType());
//	}

}

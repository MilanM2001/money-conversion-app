package com.project.services;

import com.project.domain.repositoryinterfaces.KursRepository;
import com.project.serviceinterface.KursService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KursServiceImpl implements KursService {

    KursRepository kursRepository;
    ModelMapper modelMapper;

    @Autowired
    public KursServiceImpl(KursRepository kursRepository, ModelMapper modelMapper) {}

}

package com.hotel.demo.service;

import com.hotel.demo.entity.ApParam;
import com.hotel.demo.repository.ApParamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ApParamService {

    @Autowired
    public ApParamRepository apParamRepository;

    public List<ApParam> findByType(String type) {
        return this.apParamRepository.findByType(type);
    }

    public ApParam findByCode(String code) {
        return this.apParamRepository.findByCode(code);
    }


}
package com.hotel.demo.repository;

import com.hotel.demo.entity.ApParam;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ApParamRepository extends CrudRepository<ApParam, Integer> {

    List<ApParam> findByType(String type);

    ApParam findByCode(String code);
}
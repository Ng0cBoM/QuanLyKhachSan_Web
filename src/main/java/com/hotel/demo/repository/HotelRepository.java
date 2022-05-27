package com.hotel.demo.repository;

import com.hotel.demo.entity.Hotel;
import org.springframework.data.repository.CrudRepository;

public interface HotelRepository extends CrudRepository<Hotel, Integer> {
}
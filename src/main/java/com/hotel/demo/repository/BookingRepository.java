package com.hotel.demo.repository;

import com.hotel.demo.entity.Booking;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookingRepository extends CrudRepository<Booking, Integer> {
    List<Booking> findByUsername(String username);
}
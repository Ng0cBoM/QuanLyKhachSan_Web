package com.hotel.demo.repository;

import com.hotel.demo.entity.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Integer> {
}
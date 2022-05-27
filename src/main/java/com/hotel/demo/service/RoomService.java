package com.hotel.demo.service;

import com.hotel.demo.dto.ResultDTO;
import com.hotel.demo.entity.Room;
import com.hotel.demo.repository.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoomService {

    @Autowired
    public RoomRepository roomRepository;

    @Autowired
    private EntityManager entityManager;

    public ResultDTO<Room> save(Room entity) {
        try {
            log.info("insert Room: {}", entity);
            Room save = this.roomRepository.save(entity);
            log.info("insert Room successfully");
            return new ResultDTO<>(save, false, "");
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResultDTO<>(null, true, "Thêm mới thất bại");
        }
    }

    public ResultDTO<Room> update(Integer id, Room entity) {
        try {
            log.info("update Room ID: {} -> {}", id, entity);
            entity.setId(id);
            Room save = this.roomRepository.save(entity);
            log.info("update Room successfully");
            return new ResultDTO<>(save, false, "");
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResultDTO<>(null, true, "Cập nhật thất bại");
        }
    }

    public ResponseEntity deleteById(Integer id) {
        try {
            log.info("delete Room ID: {}", id);
            Optional<Room> RoomOpt = this.roomRepository.findById(id);

            if (!RoomOpt.isPresent()) {
                throw new IllegalArgumentException("Not found id Room: " + id);
            }

            this.roomRepository.deleteById(id);
            log.info("delete Room successfully");
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public Room findById(Integer id) {
        log.info("find Room by ID: {}", id);
        Optional<Room> RoomOpt = this.roomRepository.findById(id);

        if (!RoomOpt.isPresent()) {
            throw new IllegalArgumentException("Not found id Room: " + id);
        }
        log.info("Found Room: {}", RoomOpt);
        return RoomOpt.get();
    }

    public Iterable<Room> findAll() {
        try {
            log.info("find all Room");
            Iterable<Room> RoomCollection = this.roomRepository.findAll();
            log.info("Found Room: {}", RoomCollection);
            return RoomCollection;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
    }

    public List<Room> search(Room r) {

        StringBuilder sqlSearch = new StringBuilder("SELECT room.id, room.people, room.type_room_id, room.price, room.description, room.hotel_id, room.image FROM room WHERE 1 = 1");
        Map<String, Object> params = new HashMap<>();

        if (Objects.nonNull(r.getHotelId())) {
            sqlSearch.append(" AND hotel_id = :hotelId");
            params.put("hotelId", r.getHotelId());
        }

        if (Objects.nonNull(r.getTypeRoomId())) {
            sqlSearch.append(" AND type_room_id = :typeRoomId");
            params.put("typeRoomId", r.getTypeRoomId());
        }

        if (Objects.nonNull(r.getPeople())) {
            sqlSearch.append(" AND people = :people");
            params.put("people", r.getPeople());
        }

        Query query = this.entityManager.createNativeQuery(sqlSearch.toString());
        params.forEach(query::setParameter);
        List<Object[]> resultList = query.getResultList();
        return resultList.stream().map(obj ->
            new Room(obj)
        ).collect(Collectors.toList());
    }
}
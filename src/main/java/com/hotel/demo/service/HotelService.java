package com.hotel.demo.service;

import com.hotel.demo.dto.ResultDTO;
import com.hotel.demo.entity.Hotel;
import com.hotel.demo.repository.HotelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class HotelService {

    @Autowired
    public HotelRepository hotelRepository;

    public ResultDTO<Hotel> save(Hotel entity) {
        try {
            log.info("insert Hotel: {}", entity);
            Hotel save = this.hotelRepository.save(entity);
            log.info("insert Hotel successfully");
            return new ResultDTO<>(save, false, "");
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResultDTO<>(null, true, "Thêm mới thất bại");
        }
    }

    public ResultDTO<Hotel> update(Integer id, Hotel entity) {
        try {
            log.info("update Hotel ID: {} -> {}", id, entity);
            entity.setId(id);
            Hotel save = this.hotelRepository.save(entity);
            log.info("update Hotel successfully");
            return new ResultDTO<>(save, false, "");
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResultDTO<>(null, true, "Cập nhật chi nhánh thất bại");
        }
    }

    public ResponseEntity deleteById(Integer id) {
        try {
            log.info("delete Hotel ID: {}", id);
            Optional<Hotel> HotelOpt = this.hotelRepository.findById(id);

            if (!HotelOpt.isPresent()) {
                throw new IllegalArgumentException("Not found id Hotel: " + id);
            }

            this.hotelRepository.deleteById(id);
            log.info("delete Hotel successfully");
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public Hotel findById(Integer id) {
        Optional<Hotel> HotelOpt = this.hotelRepository.findById(id);
        if (!HotelOpt.isPresent()) {
            throw new IllegalArgumentException("Not found id Hotel: " + id);
        }
        log.info("Found Hotel: {}", HotelOpt);
        return HotelOpt.get();
    }

    public Iterable<Hotel> findAll() {
        return this.hotelRepository.findAll();
    }

}
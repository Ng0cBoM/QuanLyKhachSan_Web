package com.hotel.demo.service;

import com.hotel.demo.dto.ResultDTO;
import com.hotel.demo.entity.TypeRoom;
import com.hotel.demo.repository.TypeRoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class TypeRoomService {

    @Autowired
    public TypeRoomRepository typeRoomRepository;

    public ResultDTO<TypeRoom> save(TypeRoom entity) {
        try {
            log.info("insert TypeRoom: {}", entity);
            TypeRoom save = this.typeRoomRepository.save(entity);
            log.info("insert TypeRoom successfully");
            return new ResultDTO<>(save, false, "");
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResultDTO<>(null, true, "Thêm mới thất bại");
        }
    }

    public ResultDTO<TypeRoom> update(Integer id, TypeRoom entity) {
        try {
            log.info("update TypeRoom ID: {} -> {}", id, entity);
            entity.setId(id);
            TypeRoom save = this.typeRoomRepository.save(entity);
            log.info("update TypeRoom successfully");
            return new ResultDTO<>(save, false, "");
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResultDTO<>(null, true, "Cập nhật thất bại");
        }
    }

    public ResponseEntity deleteById(Integer id) {
        try {
            log.info("delete TypeRoom ID: {}", id);
            Optional<TypeRoom> TypeRoomOpt = this.typeRoomRepository.findById(id);

            if (!TypeRoomOpt.isPresent()) {
                throw new IllegalArgumentException("Not found id TypeRoom: " + id);
            }

            this.typeRoomRepository.deleteById(id);
            log.info("delete TypeRoom successfully");
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public TypeRoom findById(Integer id) {
        log.info("find TypeRoom by ID: {}", id);
        Optional<TypeRoom> TypeRoomOpt = this.typeRoomRepository.findById(id);

        if (!TypeRoomOpt.isPresent()) {
            throw new IllegalArgumentException("Not found id TypeRoom: " + id);
        }
        log.info("Found TypeRoom: {}", TypeRoomOpt);
        return TypeRoomOpt.get();
    }

    public Iterable<TypeRoom> findAll() {
        try {
            log.info("find all TypeRoom");
            Iterable<TypeRoom> TypeRoomCollection = this.typeRoomRepository.findAll();
            log.info("Found TypeRoom: {}", TypeRoomCollection);
            return TypeRoomCollection;
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return null;
        }
    }

}
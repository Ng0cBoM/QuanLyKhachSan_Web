package com.hotel.demo.service;

import com.hotel.demo.dto.ResultDTO;
import com.hotel.demo.entity.Booking;
import com.hotel.demo.entity.Room;
import com.hotel.demo.repository.BookingRepository;
import com.hotel.demo.repository.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;

@Service
@Slf4j
public class BookingService {

    @Autowired
    public BookingRepository bookingRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RoomRepository roomRepository;

    public ResponseEntity save(Booking entity) {
        try {
            log.info("insert Booking: {}", entity);
            Booking save = this.bookingRepository.save(entity);
            log.info("insert Booking successfully");
            return ResponseEntity.status(HttpStatus.OK).body(save);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public List<Booking> findByUsername(String username) {
        return this.bookingRepository.findByUsername(username);
    }

    public ResponseEntity findAll() {
        try {
            log.info("find all Booking");
            Iterable<Booking> BookingCollection = this.bookingRepository.findAll();
            log.info("Found Booking: {}", BookingCollection);
            return ResponseEntity.status(HttpStatus.OK).body(BookingCollection);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResultDTO book(Booking booking) {
        try {
            if (booking.getFromDate().getTime() > booking.getToDate().getTime()) {
                return new ResultDTO(null, true, "Ngày không hợp lệ");
            }
            booking.setBookDate(new Date());
            Booking save = this.bookingRepository.save(booking);
            return new ResultDTO(save, false, "");
        } catch(Exception ex) {
            return new ResultDTO(null, true, "Đặt phòng thất bại");
        }
    }

    public List<Object[]> search(Room room) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sqlSearch =
                new StringBuilder("SELECT booking.username, booking.book_date, booking.price FROM booking")
                .append(" JOIN room ON room.id = booking.room_id")
                .append(" WHERE 1 = 1");

        if (Objects.nonNull(room.getTypeRoomId())) {
            sqlSearch.append(" AND room.type_room_id = :typeRoomId");
            params.put("typeRoomId", room.getTypeRoomId());
        }

        if (Objects.nonNull(room.getHotelId())) {
            sqlSearch.append(" AND room.hotel_id = :hotelId");
            params.put("hotelId", room.getHotelId());
        }

        if (Objects.nonNull(room.getPeople())) {
            sqlSearch.append(" AND room.people = :people");
            params.put("people", room.getPeople());
        }

        Query query = this.entityManager.createNativeQuery(sqlSearch.toString());
        params.forEach(query::setParameter);

        List<Object[]> result = query.getResultList();
        return result;
    }

}
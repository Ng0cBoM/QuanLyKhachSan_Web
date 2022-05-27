package com.hotel.demo.dto;

import com.hotel.demo.entity.Booking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private Integer id;
    private Date bookDate;
    private String username;
    private Integer roomId;
    private String addressHotel;
    private String numberPeople;
    private String typeRoom;
    private Float price;
    private String image;
    private Date fromDate;
    private Date toDate;

    public BookingDTO(Booking booking) {
        this.id = booking.getId();
        this.bookDate = booking.getBookDate();
        this.username = booking.getUsername();
        this.roomId = booking.getRoomId();
        this.price = booking.getPrice();
        this.fromDate = booking.getFromDate();
        this.toDate = booking.getToDate();
    }
}
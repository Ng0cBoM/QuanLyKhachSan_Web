package com.hotel.demo.dto;

import com.hotel.demo.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    private Integer id;
    private String numberPeople;
    private String typeRoom;
    private Float price;
    private String description;
    private String addressHotel;
    private String image;

    public RoomDTO(Room room) {
        this.id = room.getId();
        this.price = room.getPrice();
        this.description = room.getDescription();
        this.image = room.getImage();
    }
}
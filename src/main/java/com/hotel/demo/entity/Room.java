package com.hotel.demo.entity;

import com.hotel.demo.util.DataUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Room")
public class Room {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "people")
    private String people;

    @Column(name = "type_room_id")
    private Integer typeRoomId;

    @Column(name = "price")
    private Float price;

    @Column(name = "hotel_id")
    private Integer hotelId;

    @Column(name = "image")
    private String image;

    @Column(name = "description")
    private String description;

    public Room(Object[] objects) {
        if (Objects.isNull(objects)) {
            return;
        }
        this.id = DataUtil.safeToInt(objects[0]);
        this.people = DataUtil.safeToString(objects[1]);
        this.typeRoomId = DataUtil.safeToInt(objects[2]);
        this.price = DataUtil.safeToFloat(objects[3]);
        this.description = DataUtil.safeToString(objects[4]);
        this.hotelId = DataUtil.safeToInt(objects[5]);
        this.image = DataUtil.safeToString(objects[6]);

    }
}

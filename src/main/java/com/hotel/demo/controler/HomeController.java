package com.hotel.demo.controler;

import com.hotel.demo.constant.AppConstant;
import com.hotel.demo.dto.RoomDTO;
import com.hotel.demo.entity.ApParam;
import com.hotel.demo.entity.Hotel;
import com.hotel.demo.entity.Room;
import com.hotel.demo.entity.TypeRoom;
import com.hotel.demo.service.ApParamService;
import com.hotel.demo.service.HotelService;
import com.hotel.demo.service.RoomService;
import com.hotel.demo.service.TypeRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private ApParamService apParamService;

    @Autowired
    private TypeRoomService typeRoomService;

    @Autowired
    private HotelService hotelService;

    @GetMapping(value = {"/home", "/"})
    public String indexGuest(Model model, Room r) {
        List<RoomDTO> list = new ArrayList<>();
        for (Room room : this.roomService.search(r)) {
            RoomDTO roomDTO = new RoomDTO(room);

            ApParam apParam = this.apParamService.findByCode(room.getPeople());
            roomDTO.setNumberPeople(apParam.getValue());

            TypeRoom typeRoom = this.typeRoomService.findById(room.getTypeRoomId());
            roomDTO.setTypeRoom(typeRoom.getName());

            Hotel hotel = this.hotelService.findById(room.getHotelId());
            roomDTO.setAddressHotel(hotel.getAddress());

            list.add(roomDTO);
        }
        model.addAttribute("room", r);
        model.addAttribute("hotels", this.hotelService.findAll());
        model.addAttribute("numberPeoples", this.apParamService.findByType(AppConstant.AP_PARAM_NUMBER_PEOPLE));
        model.addAttribute("typeRooms", this.typeRoomService.findAll());
        model.addAttribute("rooms", list);
        return "guest/home";
    }

    @GetMapping("/owner/")
    public String indexOwner(Model model) {

        return "owner/home";
    }
}
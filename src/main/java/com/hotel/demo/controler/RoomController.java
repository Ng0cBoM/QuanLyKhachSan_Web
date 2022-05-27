package com.hotel.demo.controler;

import com.hotel.demo.constant.AppConstant;
import com.hotel.demo.dto.ResultDTO;
import com.hotel.demo.dto.RoomDTO;
import com.hotel.demo.entity.ApParam;
import com.hotel.demo.entity.Hotel;
import com.hotel.demo.entity.Room;
import com.hotel.demo.entity.TypeRoom;
import com.hotel.demo.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/owner/room")
public class RoomController {

    @Autowired
    private RoomService RoomService;

    @Autowired
    private TypeRoomService typeRoomService;

    @Autowired
    private ApParamService apParamService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private ImageService imageService;

    @GetMapping
    public String index(Model model) {
        List<RoomDTO> list = new ArrayList<>();
        for (Room room : this.RoomService.findAll()) {
            RoomDTO roomDTO = new RoomDTO(room);

            ApParam apParam = this.apParamService.findByCode(room.getPeople());
            roomDTO.setNumberPeople(apParam.getValue());

            TypeRoom typeRoom = this.typeRoomService.findById(room.getTypeRoomId());
            roomDTO.setTypeRoom(typeRoom.getName());

            Hotel hotel = this.hotelService.findById(room.getHotelId());
            roomDTO.setAddressHotel(hotel.getAddress());

            list.add(roomDTO);
        }
        model.addAttribute("rooms", list);
        return "owner/room/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("room", new Room());
        model.addAttribute("typeRooms", this.typeRoomService.findAll());
        model.addAttribute("numberPeoples", this.apParamService.findByType(AppConstant.AP_PARAM_NUMBER_PEOPLE));
        model.addAttribute("hotels", this.hotelService.findAll());
        return "owner/room/form";
    }

    @PostMapping("/create")
    public String onCreate(@ModelAttribute Room room, @RequestParam MultipartFile imageRoom, Model model) {
        String imagePath = this.imageService.storeImage(imageRoom);
        if (Objects.nonNull(imagePath)) {
            room.setImage(imagePath);
        }
        ResultDTO<Room> saveResult = this.RoomService.save(room);
        if (saveResult.isError()) {
            model.addAttribute("error", saveResult.getMessage());
            model.addAttribute("hotel", room);
            return "owner/room/form";
        }
        return "redirect:/owner/room";
    }

    @GetMapping("/{id}")
    public String update(@PathVariable Integer id, Model model) {
        Room room = this.RoomService.findById(id);
        model.addAttribute("room", room);
        model.addAttribute("typeRooms", this.typeRoomService.findAll());
        model.addAttribute("numberPeoples", this.apParamService.findByType(AppConstant.AP_PARAM_NUMBER_PEOPLE));
        model.addAttribute("hotels", this.hotelService.findAll());
        return "owner/room/form";
    }

    @PostMapping("/{id}")
    public String onUpdate(@ModelAttribute Room room, Model model) {
        ResultDTO<Room> resultUpdate = this.RoomService.update(room.getId(), room);
        if (resultUpdate.isError()) {
            model.addAttribute("error", resultUpdate.getMessage());
            model.addAttribute("room", room);
            return "owner/room/form";
        }
        return "redirect:/owner/room";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        this.RoomService.deleteById(id);
        return "redirect:/owner/room/index";
    }
}
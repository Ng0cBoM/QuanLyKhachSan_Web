package com.hotel.demo.controler;

import com.hotel.demo.dto.ResultDTO;
import com.hotel.demo.entity.Hotel;
import com.hotel.demo.entity.TypeRoom;
import com.hotel.demo.service.TypeRoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping("/owner/type-room")
public class TypeRoomController {

    @Autowired
    private TypeRoomService TypeRoomService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("typeRooms", this.TypeRoomService.findAll());
        return "owner/type-room/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("typeRoom", new TypeRoom());
        return "owner/type-room/form";
    }

    @PostMapping("/create")
    public String onCreate(@ModelAttribute TypeRoom typeRoom, Model model) {
        ResultDTO<TypeRoom> saveResult = this.TypeRoomService.save(typeRoom);
        if (saveResult.isError()) {
            model.addAttribute("error", saveResult.getMessage());
            model.addAttribute("typeRoom", typeRoom);
            return "owner/type-room/form";
        }
        return "redirect:/owner/type-room";
    }

    @GetMapping("/{id}")
    public String update(@PathVariable Integer id, Model model) {
        TypeRoom typeRoom = this.TypeRoomService.findById(id);
        model.addAttribute("typeRoom", typeRoom);
        return "owner/type-room/form";
    }

    @PostMapping("/{id}")
    public String onUpdate(@ModelAttribute TypeRoom typeRoom, Model model) {
        ResultDTO<TypeRoom> resultUpdate = this.TypeRoomService.update(typeRoom.getId(), typeRoom);
        if (resultUpdate.isError()) {
            model.addAttribute("error", resultUpdate.getMessage());
            model.addAttribute("typeRoom", typeRoom);
            return "owner/type-room/form";
        }
        return "redirect:/owner/type-room";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        this.TypeRoomService.deleteById(id);
        return "redirect:/owner/hotel/index";
    }
}
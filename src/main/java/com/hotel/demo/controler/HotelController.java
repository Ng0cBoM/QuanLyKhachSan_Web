package com.hotel.demo.controler;

import com.hotel.demo.dto.ResultDTO;
import com.hotel.demo.entity.Hotel;
import com.hotel.demo.service.HotelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@Controller
@RequestMapping("/owner/hotel")
public class HotelController {

    @Autowired
    private HotelService HotelService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("hotels", this.HotelService.findAll());
        return "owner/hotel/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("hotel", new Hotel());
        return "owner/hotel/form";
    }

    @PostMapping("/create")
    public String onCreate(@ModelAttribute Hotel hotel, Model model) {
        ResultDTO<Hotel> saveResult = this.HotelService.save(hotel);
        if (saveResult.isError()) {
            model.addAttribute("error", saveResult.getMessage());
            model.addAttribute("hotel", hotel);
            return "owner/hotel/form";
        }
        return "redirect:/owner/hotel";
    }

    @GetMapping("/{id}")
    public String update(@PathVariable Integer id, Model model) {
        Hotel hotel = this.HotelService.findById(id);
        model.addAttribute("hotel", hotel);
        return "owner/hotel/form";
    }

    @PostMapping("/{id}")
    public String onUpdate(@ModelAttribute("hotel") Hotel hotel, Model model) {
        ResultDTO<Hotel> resultUpdate = this.HotelService.update(hotel.getId(), hotel);
        if (resultUpdate.isError()) {
            model.addAttribute("error", resultUpdate.getMessage());
            model.addAttribute("hotel", hotel);
            return "owner/hotel/form";
        }
        return "redirect:/owner/hotel";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        this.HotelService.deleteById(id);
        return "redirect:/owner/hotel/index";
    }
}
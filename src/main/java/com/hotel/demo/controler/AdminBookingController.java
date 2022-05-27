package com.hotel.demo.controler;

import com.hotel.demo.constant.AppConstant;
import com.hotel.demo.entity.Room;
import com.hotel.demo.service.ApParamService;
import com.hotel.demo.service.BookingService;
import com.hotel.demo.service.HotelService;
import com.hotel.demo.service.TypeRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/owner/booking")
public class AdminBookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ApParamService apParamService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private TypeRoomService typeRoomService;

    @GetMapping
    public String index(Model model, Room room) {
        List<Object[]> search = this.bookingService.search(room);
        model.addAttribute("room", room);
        model.addAttribute("bookings", search);
        model.addAttribute("typeRooms", this.typeRoomService.findAll());
        model.addAttribute("numberPeoples", this.apParamService.findByType(AppConstant.AP_PARAM_NUMBER_PEOPLE));
        model.addAttribute("hotels", this.hotelService.findAll());
        return "owner/statistical";
    }
}
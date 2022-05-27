package com.hotel.demo.controler;

import com.hotel.demo.constant.AppConstant;
import com.hotel.demo.dto.BookingDTO;
import com.hotel.demo.dto.ResultDTO;
import com.hotel.demo.dto.RoomDTO;
import com.hotel.demo.entity.*;
import com.hotel.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService BookingService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private TypeRoomService typeRoomService;

    @Autowired
    private ApParamService apParamService;

    @Autowired
    private HttpSession session;

    @GetMapping("/history")
    public String history(Model model) {
        User user = (User) this.session.getAttribute(AppConstant.AUTH_SESSION);

        List<BookingDTO> list = this.BookingService.findByUsername(user.getUsername())
                .stream()
                .map(booking -> {
                    BookingDTO bookingDTO = new BookingDTO(booking);

                    Room room = this.roomService.findById(booking.getRoomId());
                    bookingDTO.setImage(room.getImage());

                    Hotel hotel = this.hotelService.findById(room.getHotelId());
                    bookingDTO.setAddressHotel(hotel.getAddress());

                    TypeRoom typeRoom = this.typeRoomService.findById(room.getTypeRoomId());
                    bookingDTO.setTypeRoom(typeRoom.getName());

                    ApParam apParam = this.apParamService.findByCode(room.getPeople());
                    bookingDTO.setNumberPeople(apParam.getValue());

                    return bookingDTO;
                }).collect(Collectors.toList());

        model.addAttribute("histories", list);
        return "guest/history";
    }

    @GetMapping("/{id}")
    public String book(@PathVariable Integer id, Model model) {

        User user = (User) this.session.getAttribute(AppConstant.AUTH_SESSION);

        Room room = this.roomService.findById(id);
        RoomDTO roomDTO = this.roomEntityToDto(room);

        Booking booking = new Booking();
        booking.setPrice(room.getPrice());
        booking.setRoomId(room.getId());
        booking.setUsername(user.getUsername());

        model.addAttribute("room", roomDTO);
        model.addAttribute("booking", booking);
        return "guest/booking-form";
    }

    @PostMapping("/{id}")
    public String onBook(@ModelAttribute Booking booking, Model model) {
        ResultDTO result = this.BookingService.book(booking);
        if (result.isError()) {
            Room room = this.roomService.findById(booking.getRoomId());
            RoomDTO roomDTO = this.roomEntityToDto(room);
            model.addAttribute("error", result.getMessage());
            model.addAttribute("booking", booking);
            model.addAttribute("room", roomDTO);
            return "guest/booking-form";
        }

        return "redirect:/booking/history";
    }

    protected RoomDTO roomEntityToDto(Room room) {
        RoomDTO roomDTO = new RoomDTO(room);

        ApParam apParam = this.apParamService.findByCode(room.getPeople());
        roomDTO.setNumberPeople(apParam.getValue());

        Hotel hotel = this.hotelService.findById(room.getHotelId());
        roomDTO.setAddressHotel(hotel.getAddress());

        TypeRoom typeRoom = this.typeRoomService.findById(room.getTypeRoomId());
        roomDTO.setTypeRoom(typeRoom.getName());
        return roomDTO;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormatter formatter = new DateFormatter();
        formatter.setPattern("yyyy-MM-dd");
        binder.addCustomFormatter(formatter, "fromDate", "toDate");
    }
}
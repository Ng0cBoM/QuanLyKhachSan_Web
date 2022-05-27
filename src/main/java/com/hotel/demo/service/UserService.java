package com.hotel.demo.service;

import com.hotel.demo.constant.AppConstant;
import com.hotel.demo.dto.ResultDTO;
import com.hotel.demo.entity.User;
import com.hotel.demo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    private HttpSession httpSession;

    public ResultDTO<User> save(User entity) {
        try {
            log.info("insert User: {}", entity);

            Optional<User> user = this.userRepository.findByUsername(entity.getUsername());
            if (user.isPresent()) {
                return new ResultDTO<>(null, true, entity.getUsername()+" đã tồn tại");
            }

            user = this.userRepository.findByPhone(entity.getPhone());
            if (user.isPresent()) {
                return new ResultDTO<>(null, true, "SĐT "+entity.getPhone() + " đã tồn tại");
            }

            if (!entity.getPhone().matches("(\\+84|0)[0-9]{9}")) {
                return new ResultDTO<>(null, true, "Số điện thoại không đúng định dạng");
            }

            entity.setRole(AppConstant.ROLE_GUEST);
            User save = this.userRepository.save(entity);
            log.info("insert User successfully");
            return new ResultDTO<>(save, false, "");
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResultDTO<>(null, true, "Đăng ký thất bại");
        }
    }

    public ResultDTO login(User user) {
        try {
            Optional<User> userOpt = this.userRepository.findByUsername(user.getUsername());
            if (!userOpt.isPresent()) {
                return new ResultDTO<>(null, true, "Tên tài khoản không chính xác");
            }
            User user1 = userOpt.get();
            if (!user1.getPassword().equals(user.getPassword())) {
                return new ResultDTO<>(null, true, "Mật khẩu không chính xác");
            }
            this.httpSession.setAttribute(AppConstant.AUTH_SESSION, user1);
            return new ResultDTO<User>(user1, false, null);
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResultDTO(null, true, "Có lỗi xảy ra trong quá trình xử lý");
        }
    }

    public void logout() {
        this.httpSession.removeAttribute(AppConstant.AUTH_SESSION);
    }

}
package project.infra;
import java.util.Map;
import java.util.HashMap;

import java.util.HashMap;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import project.domain.*;

//<<< Clean Arch / Inbound Adaptor

@RestController
@RequestMapping(value="/api/v1/users")
@Transactional
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/login")
    public User login(@RequestBody RequestUserRegistrationCommand request) {
        Long userId = request.getUserId();
        Long inputPw = request.getUserPw();

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저가 존재하지 않습니다."));

        // 검증은 도메인에게 맡긴다
        try {
            user.login(inputPw); 
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }

        userRepository.save(user);
        return user;
    }


    @PostMapping("/users")
        public User registerUser(@RequestBody RequestUserRegistrationCommand command) {
            User user = new User();
            user.setUserId(command.getUserId());
            user.setUserPw(command.getUserPw());
            user.setPass(false); // 기본값 false

            userRepository.save(user);
            user.requestUserRegistration(); // 이벤트 발행

            return user;
        }


    @PutMapping("/users/{id}/requestsubscription")
    public User requestSubscription(@PathVariable Long id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(() -> new Exception("No Entity Found"));
        user.requestSubscription();
        userRepository.save(user);
        return user;
    }

    @PutMapping("/users/{id}/cancelsubscription")
    public User cancelSubscription(@PathVariable Long id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(() -> new Exception("No Entity Found"));
        user.cancelSubscription();
        userRepository.save(user);
        return user;
    }

    @PostMapping("/users/{userId}/access")
    public Map<String, Object> accessBook(
            @PathVariable Long userId,
            @RequestBody BookAccessRequest request) throws Exception {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저가 존재하지 않습니다."));

        Long bookId = request.getBookId(); // 수정됨
        boolean pass = Boolean.TRUE.equals(user.getPass());

        user.checkBookAccess(bookId); // 수정됨

        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("bookId", bookId);
        response.put("access", pass ? "GRANTED" : "DENIED");

        return response;
    }
}
//>>> Clean Arch / Inbound Adaptor
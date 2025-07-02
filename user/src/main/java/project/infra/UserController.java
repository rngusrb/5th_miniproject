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

import lombok.RequiredArgsConstructor;
import project.domain.*;
import project.util.JwtUtil;

//<<< Clean Arch / Inbound Adaptor

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/users")
@Transactional
public class UserController {

    @Autowired
    UserRepository userRepository;

    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public UserDTO.Response login(@RequestBody RequestUserRegistrationCommand request) {
        Long userId = request.getUserId();
        Long inputPw = request.getUserPw();

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저가 존재하지 않습니다."));

        try {
            user.login(inputPw); 
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }

        String token = jwtUtil.generateToken(user.getUserId());

        return new UserDTO.Response(user.getUserId(), user.getUserPw(), token);
    }

    @PostMapping
    public UserDTO.Response registerUser(@RequestBody RequestUserRegistrationCommand command) {
        Optional<User> existingUser = userRepository.findById(command.getUserId());
        if (existingUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 존재하는 사용자 ID입니다.");
        }

        User user = new User();
        user.setUserId(command.getUserId());
        user.setUserPw(command.getUserPw());
        user.setPass(false); // 기본값 false

        userRepository.save(user);
        user.requestUserRegistration(); // 이벤트 발행

        String token = jwtUtil.generateToken(user.getUserId());

        return new UserDTO.Response(user.getUserId(), user.getUserPw(), token);
    }

    @PutMapping("/{id}/requestsubscription")
    public User requestSubscription(@PathVariable Long id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(() -> new Exception("No Entity Found"));
        user.requestSubscription();
        userRepository.save(user);
        return user;
    }

    @PutMapping("/{id}/cancelsubscription")
    public User cancelSubscription(@PathVariable Long id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(() -> new Exception("No Entity Found"));
        user.cancelSubscription();
        userRepository.save(user);
        return user;
    }

    @PostMapping("/{userId}/access")
    public Map<String, Object> accessBook(
        @PathVariable Long userId,
        @RequestBody BookAccessRequest request) throws Exception {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저가 존재하지 않습니다."));

        Long bookId = request.getBookId();
        boolean pass = Boolean.TRUE.equals(user.getPass());

        user.checkBookAccess(bookId);

        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("bookId", bookId);
        response.put("access", pass ? "GRANTED" : "DENIED");

        if (pass) {
            BookAccessGranted event = new BookAccessGranted();
            event.setUserId(userId);
            event.setBookId(bookId);
            event.setEventType("BookAccessGranted"); // headers['type']으로 사용됨
            event.publishAfterCommit(); 
        }
        


        return response;
    }
}
//>>> Clean Arch / Inbound Adaptor
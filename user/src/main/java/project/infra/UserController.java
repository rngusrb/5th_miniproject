package project.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.domain.*;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/users")
@Transactional
public class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(
        value = "/users/{id}/reqeustlogin",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public User reqeustLogin(
        @PathVariable(value = "id") Long id,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /user/reqeustLogin  called #####");
        Optional<User> optionalUser = userRepository.findById(id);

        optionalUser.orElseThrow(() -> new Exception("No Entity Found"));
        User user = optionalUser.get();
        user.reqeustLogin();

        userRepository.save(user);
        return user;
    }

    @RequestMapping(
        value = "/users/{id}/requestuserregistration",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public User requestUserRegistration(
        @PathVariable(value = "id") Long id,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /user/requestUserRegistration  called #####");
        Optional<User> optionalUser = userRepository.findById(id);

        optionalUser.orElseThrow(() -> new Exception("No Entity Found"));
        User user = optionalUser.get();
        user.requestUserRegistration();

        userRepository.save(user);
        return user;
    }

    @RequestMapping(
        value = "/users/{id}/requestsubscription",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public User requestSubscription(
        @PathVariable(value = "id") Long id,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /user/requestSubscription  called #####");
        Optional<User> optionalUser = userRepository.findById(id);

        optionalUser.orElseThrow(() -> new Exception("No Entity Found"));
        User user = optionalUser.get();
        user.requestSubscription();

        userRepository.save(user);
        return user;
    }

    @RequestMapping(
        value = "/users/{id}/cancelsubscription",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public User cancelSubscription(
        @PathVariable(value = "id") Long id,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /user/cancelSubscription  called #####");
        Optional<User> optionalUser = userRepository.findById(id);

        optionalUser.orElseThrow(() -> new Exception("No Entity Found"));
        User user = optionalUser.get();
        user.cancelSubscription();

        userRepository.save(user);
        return user;
    }
}
//>>> Clean Arch / Inbound Adaptor

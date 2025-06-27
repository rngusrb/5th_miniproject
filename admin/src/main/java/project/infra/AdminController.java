package project.infra;
import project.domain.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/admins")
@Transactional
public class AdminController {
    @Autowired
    AdminRepository adminRepository;

    @RequestMapping(value = "/admins/registerbook",
            method = RequestMethod.POST,
            produces = "application/json;charset=UTF-8")
    public Admin registerBook(HttpServletRequest request, HttpServletResponse response, 
        ) throws Exception {
            System.out.println("##### /admin/registerBook  called #####");
            Admin admin = new Admin();
            admin.registerBook();
            adminRepository.save(admin);
            return admin;
    }
}
//>>> Clean Arch / Inbound Adaptor

package project.infra;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import project.domain.*;

//<<< Clean Arch / Inbound Adaptor
@RestController
@Transactional
public class AdminController {

//    @Autowired
//    AdminRepository adminRepository;
//
//    @RequestMapping(value = "/admins/registerauthor",
//        method = RequestMethod.POST,
//        produces = "application/json;charset=UTF-8")
//    public Admin registerAuthor(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        System.out.println("##### /admin/registerAuthor called #####");
//
//        // 간단한 테스트용 호출 (실제 호출은 Kafka 이벤트 기반이나 수동 등록용으로 유지)
//        Admin admin = new Admin();
//        admin.setAuthorLoginId(1L); // 필요 시 request 파라미터로부터 입력받도록 변경
//        adminRepository.save(admin);
//
//        return admin;
//    }
}
//>>> Clean Arch / Inbound Adaptor

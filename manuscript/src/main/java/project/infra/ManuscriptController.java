package project.infra;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.domain.*;

//<<< Clean Arch / Inbound Adaptor

@RestController
@RequestMapping(value="/api/v1/manuscripts")
@Transactional
public class ManuscriptController {

    @Autowired
    ManuscriptRepository manuscriptRepository;

    @GetMapping
    public List<Manuscript> getManuscripts() {
        Iterable<Manuscript> iterable = manuscriptRepository.findAll();
        List<Manuscript> manuscripts = new ArrayList<>();
        iterable.forEach(manuscripts::add);
        return manuscripts;
    }

    @GetMapping("/{id}")
    public Manuscript getManuscript(@PathVariable Long id) {
        Manuscript manuscript = manuscriptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 ID의 원고가 존재하지 않습니다"));
        return manuscript;
    }

    @PostMapping
    public void registerManuscript(@RequestBody Manuscript manuscript) {
        if ("TEMP".equalsIgnoreCase(manuscript.getStatus())) {
            Optional<Manuscript> existing = manuscriptRepository.findFirstByAuthorIdAndStatus(manuscript.getAuthorId(), "TEMP");
            if (existing.isPresent()) {
                Manuscript temp = existing.get();
                temp.setTitle(manuscript.getTitle());
                temp.setContent(manuscript.getContent());
                temp.setModifyDate(LocalDateTime.now());
                // 기타 필요한 필드 업데이트
                manuscriptRepository.save(temp);
                return;
            }
        }

        // TEMP가 아니거나 기존 TEMP가 없는 경우
        if (!"TEMP".equalsIgnoreCase(manuscript.getStatus())) {
            manuscript.register(); // 최종 저장일 경우만 REGISTERED 처리
        }
        manuscriptRepository.save(manuscript);
    }


    @PatchMapping("/edit/{id}")
    public void editManuscript(@PathVariable Long id, @RequestBody Manuscript body) {
        Manuscript manuscript = manuscriptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 ID의 원고가 존재하지 않습니다"));

        manuscript.setTitle(body.getTitle());
        manuscript.setContent(body.getContent());
        manuscript.setCategory(body.getCategory());

        manuscript.edit();
        manuscriptRepository.save(manuscript);
    }

    @DeleteMapping("/{id}")
    public void deleteManuscript(@PathVariable Long id) {
        Manuscript manuscript = manuscriptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 ID의 원고가 존재하지 않습니다"));

        manuscript.delete();
        manuscriptRepository.delete(manuscript);

    }

    @PostMapping("/summary/{id}")
    public void summaryManuscript(@PathVariable Long id) {
        Manuscript manuscript = manuscriptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 ID의 원고가 존재하지 않습니다"));

        manuscript.summary();
    }

    @PostMapping("/category/{id}")
    public void categoryManuscript(@PathVariable Long id) {
        Manuscript manuscript = manuscriptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 ID의 원고가 존재하지 않습니다"));

        manuscript.createCategory();;
    }

    @PostMapping("/coverimage/{id}")
    public void coverImgManuscript(@PathVariable Long id) {
        Manuscript manuscript = manuscriptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 ID의 원고가 존재하지 않습니다"));

        manuscript.createImg();
    }
}
//>>> Clean Arch / Inbound Adaptor
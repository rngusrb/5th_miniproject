package project.infra;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public void resgisterManuscript(@RequestBody Manuscript manuscript) {
        manuscript.register();
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
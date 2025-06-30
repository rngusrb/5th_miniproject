package project.infra;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    OpenAIService openai;

    @GetMapping
    public List<ManuscriptDTO.Response> getManuscripts() {
        List<Manuscript> manuscripts = manuscriptRepository.findAll();

        return manuscripts.stream().map(ManuscriptDTO::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/{authorId}")
    public List<ManuscriptDTO.Response> getManuscript(@PathVariable Long authorId) {
        List<Manuscript> manuscripts = manuscriptRepository.findByAuthorId(authorId);

        if (manuscripts.isEmpty()) {
            throw new RuntimeException("해당 authorId의 원고가 존재하지 않습니다");
        }

        return manuscripts.stream().map(ManuscriptDTO::toResponse).collect(Collectors.toList());
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
    public ManuscriptDTO.Response summaryManuscript(@PathVariable Long id) {
        Manuscript manuscript = manuscriptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 ID의 원고가 존재하지 않습니다"));

        String prompt = "다음 글을 요약해줘:" + manuscript.getContent();
        String summary = openai.generateText(prompt);

        manuscript.setSummary(summary);
        manuscriptRepository.save(manuscript);

        return ManuscriptDTO.toResponse(manuscript);
    }

    @PostMapping("/category/{id}")
    public ManuscriptDTO.Response categoryManuscript(@PathVariable Long id) {
        Manuscript manuscript = manuscriptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 ID의 원고가 존재하지 않습니다"));

        String prompt = "다음 글의 내용을 바탕으로 가장 적절한 도서 카테고리를 하나 선택해서 알려줘.\n" +
                "가능한 카테고리 예시: 인문학, 사회, 과학, 역사, 철학, 문학, 예술, 자기계발, 종교, 기술, 정치, 경제, 여행, 건강, 취미, 에세이, 소설 등.\n" +
                "해당 도서의 핵심 주제를 고려해서 가장 알맞은 하나의 카테고리를 반환해줘." + manuscript.getContent();
        String category = openai.generateText(prompt);

        manuscript.setCategory(category);
        manuscriptRepository.save(manuscript);

        return ManuscriptDTO.toResponse(manuscript);
    }

    @PostMapping("/coverimage/{id}")
    public ManuscriptDTO.Response coverImgManuscript(@PathVariable Long id) {
        Manuscript manuscript = manuscriptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 ID의 원고가 존재하지 않습니다"));

        String prompt = "다음 글의 표지 이미지를 생성해줘:" + manuscript.getContent();
        String category = openai.generateImage(prompt);


        manuscript.setBookCoverImage(category);
        manuscriptRepository.save(manuscript);

        return ManuscriptDTO.toResponse(manuscript);
    }

    @PostMapping("/publish/{ids}")
    public void publishManuscript(@PathVariable String ids) {
        // "1,2,3" → List<Long>
        List<Long> idList = Arrays.stream(ids.split(",")).map(Long::parseLong).collect(Collectors.toList());

        List<Manuscript> manuscripts = manuscriptRepository.findAllById(idList);

        for (Manuscript manuscript : manuscripts) {
            manuscript.publish();
        }
    }
}
//>>> Clean Arch / Inbound Adaptor
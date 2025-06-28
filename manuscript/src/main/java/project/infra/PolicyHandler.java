package project.infra;

import javax.transaction.Transactional;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import project.config.KafkaProcessor;
import project.domain.*;

//<<< Clean Arch / Inbound Adaptor
// @ConditionalOnProperty(name = "kafka.enabled", havingValue = "true")
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    ManuscriptRepository manuscriptRepository;
    @Autowired
    OpenAIService openai;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(KafkaProcessor.INPUT)
    public void registerHandler(@Payload ManuscriptSubmitted event) {
        if (!event.validate()) return;

        System.out.println("원고 등록 완료:" + event.getManuscriptId());
    }
    
    @StreamListener(KafkaProcessor.INPUT)
    public void editHandler(@Payload ManuscriptEdited event) {
        if (!event.validate()) return;

        System.out.println("원고 편집 완료:" + event.getManuscriptId());
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void summaryHandler(@Payload ManuscriptSummary event) {
        if (!event.validate()) return;

        String prompt = "다음 글을 요약해줘:" + event.getContent();
        String summary = openai.generateText(prompt);

        Manuscript manuscript = manuscriptRepository.findById(event.getManuscriptId()).get();

        manuscript.setSummary(summary);
        manuscriptRepository.save(manuscript);

        System.out.println("원고 요약 완료:" + event.getManuscriptId());
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void categoryHandler(@Payload ManuscriptCategoryCreated event) {
        if (!event.validate()) return;

        String prompt = "다음 글의 카테고리를 생성해줘:" + event.getContent();
        String category = openai.generateText(prompt);

        Manuscript manuscript = manuscriptRepository.findById(event.getManuscriptId()).get();

        manuscript.setCategory(category);
        manuscriptRepository.save(manuscript);

        System.out.println("카테고리 생성 완료:" + event.getManuscriptId());
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void coverImgHandler(@Payload CoverImageCreated event) {
        if (!event.validate()) return;

        String prompt = "다음 글의 표지 이미지를 생성해줘:" + event.getContent();
        String category = openai.generateImage(prompt);

        Manuscript manuscript = manuscriptRepository.findById(event.getManuscriptId()).get();

        manuscript.setBookCoverImage(category);
        manuscriptRepository.save(manuscript);

        System.out.println("이미지 생성 완료:" + event.getManuscriptId());
    }
}
//>>> Clean Arch / Inbound Adaptor

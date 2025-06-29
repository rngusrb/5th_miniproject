package project.infra;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import project.config.kafka.KafkaProcessor;
import project.domain.*;

@Service
@Transactional
public class PointUsageHistoryViewHandler {

    @Autowired
    private PointUsageHistoryRepository repository;

    /**
     * PointUpdated 이벤트 발생 시, 포인트 사용 내역(Read Model)에 저장
     * headers['type']=='PointUpdated' 로 필터링
     */
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PointUpdated'"
    )
    public void whenPointUpdated_thenCreateUsageHistory(
        @Payload PointUpdated event
    ) {
        System.out.println("##### listener PointUsageHistory : " + event);

        // Read Model 엔터티 생성 및 필드 매핑
        PointUsageHistory history = new PointUsageHistory();
        history.setId(null); // @GeneratedValue 적용 시 자동 채번
        history.setUserId(event.getUserId());
        history.setBookId(event.getBookId());
        history.setChangePoint(event.getChangePoint());
        history.setChangeDate(event.getChangeDate());
        history.setRemainPoint(event.getRemainPoint());

        // 저장
        repository.save(history);
    }
}

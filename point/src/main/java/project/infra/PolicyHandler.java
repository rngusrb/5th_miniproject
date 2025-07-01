package project.infra;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import project.config.kafka.KafkaProcessor;
import project.domain.*;

@Service
@Transactional
public class PolicyHandler {

    @Autowired
    PointUsageHistoryRepository pointUsageHistoryRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    // 포인트 차감 (도서 접근 거부)
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='BookAccessDenied'"
    )
    public void wheneverBookAccessDenied_PointBalanceChange(
        @Payload BookAccessDenied bookAccessDenied
    ) {
        System.out.println(
            "\n\n##### listener PointBalanceChange : " +
            bookAccessDenied +
            "\n\n"
        );

        Point.pointBalanceChange(bookAccessDenied);
    }

    // 포인트 지급 (회원 가입)
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='UserRegistered'"
    )
    public void wheneverUserRegistered_PointBalanceChange(
        @Payload UserRegistered userRegistered
    ) {
        System.out.println(
            "\n\n##### listener PointBalanceChange : " + userRegistered + "\n\n"
        );

        Point.pointBalanceChange(userRegistered);
    }

    // 포인트 사용/지급 내역 저장
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='PointMinus'"
    )
    public void wheneverPointMinus_SavePointUsage(@Payload PointUpdated pointMinus) {
        if (pointMinus == null || pointMinus.getUserId() == null) return;

        System.out.println(
            "\n\n##### listener SavePointUsageHistory : " + pointMinus + "\n\n"
        );

        PointUsageHistory history = new PointUsageHistory();
        history.setUserId(pointMinus.getUserId());
        history.setChangePoint(pointMinus.getChangePoint());
        history.setPointSum(pointMinus.getPointSum());
        history.setReason(pointMinus.getReason());
        history.setChangeDate(pointMinus.getChangeDate());

        pointUsageHistoryRepository.save(history);
    }
}

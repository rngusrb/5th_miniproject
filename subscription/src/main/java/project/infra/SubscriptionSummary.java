package project.infra;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;


// ReadModel -> 프론트로 보낼 DTO 새로 생성
@Data
@AllArgsConstructor
public class SubscriptionSummary {
    private Long   userId;
    private Long   subscriptionType;
    private List<Long> bookIds;

    //
    //     [
    //{
    //    "userId": 123,
    //    "subscriptionType": 9900,
    //    "bookIds": [111,222,333]
    //}, -> json 형식 


}

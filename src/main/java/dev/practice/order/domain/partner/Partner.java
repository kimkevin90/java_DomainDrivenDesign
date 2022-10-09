package dev.practice.order.domain.partner;

import dev.practice.order.common.util.TokenGenerator;
import dev.practice.order.domain.AbstractEntity;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@Entity
@Getter
// 기본 생성자 필요
@NoArgsConstructor
@Table(name = "partners")
public class Partner extends AbstractEntity {

    private static final String PREFIX_PARTNER = "ptn_";

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    // PK와 동일한 RandomString 설정
    private String partnerToken;
    private String partnerName;
    private String businessNo;
    private String email;

    // DB에 저장시 Status가 ENABLE,DISABLE String이 되도록 함
    @Enumerated(EnumType.STRING)
    private Status status;

    // Builder를 사용하여 파라미터 순서에 따른 영향을 최소화한다.
    // 생성자를 둠으로써 Entity의 필수요소를 지정한다. ~ setter는 지양
    @Builder
    public Partner(String partnerName, String businessNo, String email) {
        // StringUtils를 사용하여 length = 0이거나(빈문자), null일때 오류 발생
        if (StringUtils.isEmpty(partnerName)) throw new RuntimeException("empty partnerName");
        if (StringUtils.isEmpty(businessNo)) throw new RuntimeException("empty businessNo");
        if (StringUtils.isEmpty(email)) throw new RuntimeException("empty email");

        this.partnerToken = TokenGenerator.randomCharacterWithPrefix(PREFIX_PARTNER);
        this.partnerName = partnerName;
        this.businessNo = businessNo;
        this.email = email;
        this.status = Status.ENABLE;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        ENABLE("활성화"), DISABLE("비활성화");
        private final String description;
    }

    public void enable() {
        this.status = Status.ENABLE;
    }

    public void disable() {
        this.status = Status.DISABLE;
    }
}

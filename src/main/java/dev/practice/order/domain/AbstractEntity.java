package dev.practice.order.domain;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.ZonedDateTime;

@Getter
// AbstractEntity룰 상속받는 객체는 자동으로 아래 2개의 컬럼이 생성된다.
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity {

    // 타임존 기반을 위해 Timestamp 사용
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;
}

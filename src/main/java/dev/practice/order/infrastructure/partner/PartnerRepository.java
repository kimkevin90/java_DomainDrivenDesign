package dev.practice.order.infrastructure.partner;

import dev.practice.order.domain.partner.Partner;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

// JPA가 아닌 DSL로 바뀔 시, ReaderImpl & StoreImple의 구현체만 바꾸면 된다.
public interface PartnerRepository extends JpaRepository<Partner, Long> {
    Optional<Partner> findByPartnerToken(String partnerToken);
}

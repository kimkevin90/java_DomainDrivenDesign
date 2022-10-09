package dev.practice.order.interfaces.partner;

import dev.practice.order.domain.partner.PartnerCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface PartnerDtoMapper {
    // target method(Source suouce)
    // RegisterRequest를 PartnerCommand로 변경한다는 뜻
    PartnerCommand of(PartnerDto.RegisterRequest request);
}


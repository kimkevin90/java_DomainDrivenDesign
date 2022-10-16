package dev.practice.order.domain.partner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    private final PartnerStore partnerStore;
    private final PartnerReader partnerReader;

    @Override
    @Transactional
    public PartnerInfo registerPartner(PartnerCommand command) {
       /* command로 initPartner 리팩토링
             var initPartner= Partner.builder()
            .partnerName(command.getPartnerName())
            .businessNo(command.getBusinessNo())
            .email(command.getEmail())
            .build();
       * */

        // 1. command -> initPartner로 만들기
        var initPartner= command.toEntity();
        // 2. initPartner save to DB
        Partner partner = partnerStore.store(initPartner);
        // 3. 가져온 Partner 정보를 -> PartnerInfo 변환 AND Return
        return new PartnerInfo(partner);
    }

    @Override
    @Transactional(readOnly = true)
    public PartnerInfo getPartnerInfo(String partnerToken) {
        // 1. partnerToken으로 Partner 받아오기
        Partner partner = partnerReader.getPartner(partnerToken);
        // 2. Partner를 PartnerInfo And Return
        return new PartnerInfo(partner);
    }

    @Override
    @Transactional
    public PartnerInfo enablePartner(String partnerToken) {
        // 1. partnerToken으로 Partner 받아오기
        // 2. Partner.enable()실행
        Partner partner = partnerReader.getPartner(partnerToken);
        partner.enable();
        return new PartnerInfo(partner);
    }

    @Override
    @Transactional
    public PartnerInfo disablePartner(String partnerToken) {
        Partner partner = partnerReader.getPartner(partnerToken);
        partner.disable();
        return new PartnerInfo(partner);
    }
}

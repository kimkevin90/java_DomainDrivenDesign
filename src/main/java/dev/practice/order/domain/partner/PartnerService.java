package dev.practice.order.domain.partner;

public interface PartnerService {
    // Service의 요구사항은 등록, 조회, 활성화, 비활성화 4개
    // Command -- 명령(등록), Criteria -- 조회 Info -- 객체 리턴
    PartnerInfo registerPartner(PartnerCommand command);
    PartnerInfo getPartnerInfo(String partnerToken);
    PartnerInfo enablePartner(String partnerToken);
    PartnerInfo disablePartner(String partnerToken);
}

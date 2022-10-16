package dev.practice.order.domain.partner;

import lombok.Getter;


@Getter
public class PartnerInfo {
    private final Long id;
    private final String partnerToken;
    private final String partnerName;
    private final String businessNo;
    private final String email;
    private final Partner.Status status;


    // Partner와 PartnerInfo는 같은 도메인에 있으므로 partner 바로 받아 적용
    public PartnerInfo(
        Partner partner) {
        this.id = partner.getId();
        this.partnerToken = partner.getPartnerToken();
        this.partnerName = partner.getPartnerName();
        this.businessNo = partner.getBusinessNo();
        this.email = partner.getEmail();
        this.status = partner.getStatus();
    }
}

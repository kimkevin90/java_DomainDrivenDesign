package dev.practice.order.interfaces.partner;

import dev.practice.order.application.partner.PartnerFacade;
import dev.practice.order.common.response.CommonResponse;
import dev.practice.order.domain.partner.PartnerCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/partners")
public class PartnerApiController {
    private final PartnerFacade partnerFacade;
    private final PartnerDtoMapper partnerDtoMapper;

    @PostMapping
    public CommonResponse registerPartner(@RequestBody PartnerDto.RegisterRequest request) {
        System.out.println("request :" + request);
        // 1. 외부에서 전달된 파라미터(dto) -> Command, Criteria 로 convert
        // 1) Dto의 toCommand로 변환방법
        // var command = request.toCommand();
        // 2) Mapper 라이브러리로 변환 방법
        var command = partnerDtoMapper.of(request);
        System.out.println("command :" + command);
        // 2. facade 호출 후 partnerInfo 리턴
        var partnerInfo = partnerFacade.registerPartner(command);
        System.out.println("partnerInfo :" + partnerInfo);
        var response = new PartnerDto.RegisterResponse(partnerInfo);
        System.out.println("response :" + response);
        // 3. partnerInfo를 CommonResponse로 convert하고 return한다.
        return CommonResponse.success(response);
    }
}

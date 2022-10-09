package dev.practice.order.domain.item;

import dev.practice.order.domain.partner.PartnerReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final PartnerReader partnerReader;
    private final ItemStore itemStore;
    private final ItemReader itemReader;
    private final ItemOptionSeriesFactory itemOptionSeriesFactory;

    @Override
    @Transactional
    // 1.2.3을 facade로 추상화를 높이면 오히려 서비스에 대한 이해를 헤칠 수 있다.
    public String registerItem(ItemCommand.RegisterItemRequest command, String partnerToken) {
        // 1. get partnerId
        var partner = partnerReader.getPartner(partnerToken);

        // 2. item store
        var initItem = command.toEntity(partner.getId());
        var item = itemStore.store(initItem);

        // 3. itemOptionGroup 과 itemOption을 저장
        /**
         * 리팩토링 전
         * command.getItemOptionGroupRequestList().forEach(requestItemOptionGroup -> {
         *                     // itemOptionGroup store
         *                     var initItemOptionGroup = ItemOptionGroup.builder()
         *                             .item(item)
         *                             .ordering(requestItemOptionGroup.getOrdering())
         *                             .itemOptionGroupName(requestItemOptionGroup.getItemOptionGroupName())
         *                             .build();
         *                     var itemOptionGroup = itemOptionGroupStore.store(initItemOptionGroup);
         *
         *                     // itemOption store
         *                     requestItemOptionGroup.getItemOptionRequestList().forEach(requestItemOption -> {
         *                         var initItemOption = ItemOption.builder()
         *                                 .itemOptionGroup(itemOptionGroup)
         *                                 .ordering(requestItemOption.getOrdering())
         *                                 .itemOptionName(requestItemOption.getItemOptionName())
         *                                 .itemOptionPrice(requestItemOption.getItemOptionPrice())
         *                                 .build();
         *                         itemOptionStore.store(initItemOption);
         *                     });
         *                 });
         **/

        itemOptionSeriesFactory.store(command, item);
        return item.getItemToken();
    }

    @Override
    @Transactional
    public void changeOnSale(String itemToken) {
        var item = itemReader.getItemBy(itemToken);
        item.changeOnSale();
    }

    @Override
    @Transactional
    public void changeEndOfSale(String itemToken) {
        var item = itemReader.getItemBy(itemToken);
        item.changeEndOfSale();
    }

    @Override
    @Transactional(readOnly = true)
    public ItemInfo.Main retrieveItemInfo(String itemToken) {
        // 1. item 정보 retrieve
        var item = itemReader.getItemBy(itemToken);
        // 2. itemOptionGroup 정보 retrive
        var itemOptionGroupInfoList = itemReader.getItemOptionSeries(item);
        // 3. item과 itemOptionGroupList 리턴
        return new ItemInfo.Main(item, itemOptionGroupInfoList);
    }
}

package dev.practice.order.domain.item.option;


import dev.practice.order.common.exception.InvalidParamException;
import dev.practice.order.domain.AbstractEntity;
import dev.practice.order.domain.item.optiongroup.ItemOptionGroup;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@Getter
@Entity
@NoArgsConstructor
@Table(name = "item_options")
public class ItemOption extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_option_group_id")
    private ItemOptionGroup itemOptionGroup;
    private Integer ordering;
    private String itemOptionName;
    private Long itemOptionPrice;

    // ItemOption은 itemOptionGroup이 존재해야하므로 조건 추가
    @Builder
    public ItemOption(
            ItemOptionGroup itemOptionGroup,
            Integer ordering,
            String itemOptionName,
            Long itemOptionPrice
    ) {
        if (itemOptionGroup == null) throw new InvalidParamException("ItemOption.itemOptionGroup");
        if (ordering == null) throw new InvalidParamException("ItemOption.ordering");
        if (StringUtils.isBlank(itemOptionName)) throw new InvalidParamException("ItemOption.itemOptionName");
        if (itemOptionPrice == null) throw new InvalidParamException("ItemOption.itemOptionPrice");

        this.itemOptionGroup = itemOptionGroup;
        this.ordering = ordering;
        this.itemOptionName = itemOptionName;
        this.itemOptionPrice = itemOptionPrice;
    }
}

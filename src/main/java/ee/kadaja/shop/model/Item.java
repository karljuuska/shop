package ee.kadaja.shop.model;


import java.time.OffsetDateTime;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import ee.kadaja.shop.model.type.ItemType;
import ee.kadaja.shop.statemachine.ItemState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;


@Data
@Entity
@DynamicUpdate //Needs use case checking
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Size(min = 3)
    @Column(name = "item_name")
    private String itemName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "item_state")
    private ItemState itemState;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "item_type")
    private ItemType itemType;

    @NotNull
    @Column(name = "item_price")
    private Double itemPrice;

    @NotNull
    @Min(0)
    @Column(name = "item_amount")
    private Double itemAmount;

    @NotNull
    @PastOrPresent
    @Column(name = "updated_on")
    private OffsetDateTime updatedOn;
}

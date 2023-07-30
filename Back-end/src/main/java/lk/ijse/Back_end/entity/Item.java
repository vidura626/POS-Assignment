package lk.ijse.Back_end.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Item {
    private String itemCode;
    private String itemName;
    private int itemQty;
    private double itemUnitPrice;
}
package lk.ijse.Back_end.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ItemDTO {
    private String itemCode;
    private String itemName;
    private int itemQty;
    private double itemUnitPrice;
}

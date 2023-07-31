package lk.ijse.Back_end.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetailsDTO {
    private int id;
    private String order_Id;
    private String orItemCOde;
    private int orItemQTY;
    private double orItemPrice;
    private double orItemTotal;

    public OrderDetailsDTO(String order_Id, String orItemCOde, int orItemQTY, double orItemPrice, double orItemTotal) {
        this.order_Id = order_Id;
        this.orItemCOde = orItemCOde;
        this.orItemQTY = orItemQTY;
        this.orItemPrice = orItemPrice;
        this.orItemTotal = orItemTotal;
    }
}

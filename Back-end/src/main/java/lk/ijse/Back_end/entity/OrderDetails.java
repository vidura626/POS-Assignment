package lk.ijse.Back_end.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetails {
    private int id;
    private String order_Id;
    private String orItemCOde;
    private int orItemQTY;
    private double orItemPrice;
    private double orItemTotal;
}

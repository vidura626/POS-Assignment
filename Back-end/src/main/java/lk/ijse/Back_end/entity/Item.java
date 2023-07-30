package lk.ijse.Back_end.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Item {
    private String code;
    private String name;
    private int qty;
    private double unit_price;
}

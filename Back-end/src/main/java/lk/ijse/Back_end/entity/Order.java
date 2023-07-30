package lk.ijse.Back_end.entity;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Order {
    private String oId;
    private String custId;
    private Date date;
    private double discount;
    private double subtotal;
}

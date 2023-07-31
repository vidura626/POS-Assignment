package lk.ijse.Back_end.entity;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Order {
    private String orId;
    private String orCusId;
    private Date orDate;
    private double orDis;
    private double orSubTotal;
}

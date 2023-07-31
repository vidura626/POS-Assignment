package lk.ijse.Back_end.dto;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDTO {
    private String orId;
    private String orCusId;
    private Date orDate;
    private double orDis;
    private double orSubTotal;
}

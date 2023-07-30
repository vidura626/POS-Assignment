package lk.ijse.Back_end.dto;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDTO {
    private String oId;
    private String custId;
    private Date date;
    private double discount;
    private double subtotal;
}

package lk.ijse.Back_end.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Customer {
    private String custID;
    private String custAddress;
    private String custName;
    private double custSalary;
}

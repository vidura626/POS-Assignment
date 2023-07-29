package lk.ijse.Back_end.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerDTO {
    private String custID;
    private String custName;
    private String custAddress;
    private double custSalary;
}

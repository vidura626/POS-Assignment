package lk.ijse.Back_end.dto;

import jakarta.json.bind.annotation.JsonbDateFormat;
import lombok.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDTO {
    private String orId;
    private String orCusId;
    private String orCusName;
    @JsonbDateFormat(value = "yyyy-MM-dd")
    private java.util.Date orDate;
    private double orDis;
    private double orSubTotal;
    private List<OrderDetailsDTO> orderDetails;


    public OrderDTO(String orId, String orCusName, java.util.Date orDate, double orDis, double orSubTotal, List<OrderDetailsDTO> orderDetails) {
        this.orId = orId;
        this.orCusName = orCusName;
        this.orDate = orDate;
        this.orDis = orDis;
        this.orSubTotal = orSubTotal;
        this.orderDetails = orderDetails;
    }

    public OrderDTO(String orId, String orCusId, Date orDate, double orDis, double orSubTotal) {
        this.orId = orId;
        this.orCusId = orCusId;
        this.orDate = orDate;
        this.orDis = orDis;
        this.orSubTotal = orSubTotal;
    }

}

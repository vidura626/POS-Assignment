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
    @JsonbDateFormat(value = "yyyy-MM-dd")
    private java.util.Date orDate;
    private double orDis;
    private double orSubTotal;
    private List<OrderDetailsDTO> orderDetails;

    public OrderDTO(String orId, String orCusId, Date orDate, double orDis, double orSubTotal) {
        this.orId = orId;
        this.orCusId = orCusId;
        this.orDate = orDate;
        this.orDis = orDis;
        this.orSubTotal = orSubTotal;
    }
}

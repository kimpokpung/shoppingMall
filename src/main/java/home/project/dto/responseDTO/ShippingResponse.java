package home.project.dto.responseDTO;

import home.project.domain.DeliveryStatusType;
import home.project.domain.DeliveryType;
import home.project.dto.requestDTO.ProductDTOForOrder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ShippingResponse {

    private Long id;

    private String deliveryNum;

    private LocalDateTime orderDate;

    private String deliveryAddress;

    private Long totalAmount;

    private List<ProductDTOForOrder> products;

    private DeliveryType deliveryType;

    private DeliveryStatusType deliveryStatusType;

    private Long deliveryCost;

    private String memberEmail;


    public ShippingResponse(Long id, String deliveryNum, LocalDateTime orderDate, String deliveryAddress, Long totalAmount, List<ProductDTOForOrder> products, DeliveryType deliveryType, DeliveryStatusType deliveryStatusType, Long deliveryCost, String memberEmail) {
        this.id = id;
        this.deliveryNum = deliveryNum;
        this.orderDate = orderDate;
        this.deliveryAddress = deliveryAddress;
        this.totalAmount = totalAmount;
        this.products = products;
        this.deliveryType = deliveryType;
        this.deliveryStatusType = deliveryStatusType;
        this.deliveryCost = deliveryCost;
        this.memberEmail = memberEmail;
    }
}

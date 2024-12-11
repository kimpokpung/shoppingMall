package home.project.dto.responseDTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class ProductCouponResponse {

    private Long id;

    private String productNum;

    private Long couponId;

    private LocalDateTime issuedAt;

    private LocalDateTime usedAt;

    private Integer discountRate;

    private boolean isUsed;

    public ProductCouponResponse(Long id, String productNum, Long couponId, LocalDateTime issuedAt, LocalDateTime usedAt, boolean isUsed){
        this.id = id;
        this.productNum = productNum;
        this.couponId = couponId;
        this.issuedAt = issuedAt;
        this.usedAt = usedAt;
        this.isUsed = isUsed;
    }
}

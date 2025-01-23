package com.jewellery.entities;

import com.jewellery.constant.OrderStatus;
import com.jewellery.constant.PaymentStatus;
import com.jewellery.constant.PaymentType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class OrderItemEntity extends BaseEntity{
    Double price;
    Integer quantity;
    String orderCode;
    @Enumerated(EnumType.STRING)
    PaymentStatus paymentStatus;
    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;
    @Temporal(TemporalType.DATE)
    Date createdDate;
    @ManyToOne
    ProductEntity productEntity;
    @ManyToOne
    UserEntity userEntity;
    @Temporal(TemporalType.DATE)
    Date orderDate;
    String paymentId;
    @Enumerated(EnumType.STRING)
    PaymentType paymentType;
    String promoCode;
    @ManyToOne
    AddressEntity address;
}
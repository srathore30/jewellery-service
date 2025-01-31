package com.jewellery.entities;

import com.jewellery.constant.DesignType;
import com.jewellery.constant.FinishType;
import com.jewellery.constant.StoneType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpecificationEntity extends BaseEntity{
    @OneToOne
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private ProductEntity product;

    @Column(nullable = false, length = 50)
    private String silverPurity; // e.g., "925 Sterling"

    @Column(nullable = false, precision = 8, scale = 2)
    private Double weight; // in grams

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private StoneType stoneType; // Default to NONE if no stone

    @Column(length = 50)
    private String size; // e.g., "7 inches"

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private FinishType finishType;  // Default to Polished

    @Column(nullable = false)
    private boolean hallmark;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private DesignType designType; // Default to Traditional
}

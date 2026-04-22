package com.tanmay.buyit.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest {

    @NotNull
    private Long productId;

    @NotNull
    @Positive
    @Max(99)
    private Integer quantity;
}

package com.tanmay.buyit.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Product Id", example = "99")
    private Long productId;

    @NotNull
    @Positive
    @Max(99)
    @Schema(description = "Product Quantity", example = "999")
    private Integer quantity;
}

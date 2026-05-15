package com.tanmay.buyit.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

    @NotBlank
    @Size(min = 3, max = 50)
    @Schema(description = "Product name", example = "iPhone 18")
    private String name;

    @NotBlank
    @Size(min = 3, max = 100)
    @Schema(description = "Product description", example = "iPhone 18 the brand new same phone")
    private String description;

    @NotNull
    @DecimalMin("0.01")
    @Schema(description = "Product price", example = "99999")
    private BigDecimal price;

    @NotNull
    @Min(0)
    @Schema(description = "Product stock", example = "999")
    private Integer stock;

    @NotNull
    @Schema(description = "Product category id", example = "18")
    private Long categoryId;
}

package com.tanmay.buyit.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "buyit_roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;       //eg -> Admin/Customer
}

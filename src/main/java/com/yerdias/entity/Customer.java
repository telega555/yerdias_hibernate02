package com.yerdias.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    @Column(name="name")
    private String userName;
    @Column(name = "passwrod")
    private String password;
    @Column(name = "amount_of_money")
    private BigDecimal amountOfMoney;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;


}

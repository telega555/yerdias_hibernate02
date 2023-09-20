package com.yerdias.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "place")
    private Integer place;
    @Column
    private boolean IsPurchased;

    @ManyToOne
    private Customer user;
    @ManyToOne
    private Film film;

    public void setIsPurchased(boolean b) {
    }

    public boolean getIsPurchased() {
        return false;
    }

    public void setCustumer(Customer currentUser) {
    }



}

package com.poly.Slide7.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name= "Product")
public class product {
    @Id
    @Column(name="id")
    private int id;

    @Column(name="name")
    private  String Name;

    @Column(name="category")
    private String category;

    @Column(name="quantity")
    private int quantity;

    @Column(name="UnitPrice")
    private double unitPrice;

    @Column(name="price")
    private  double price;

}

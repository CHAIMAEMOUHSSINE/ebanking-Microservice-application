package net.chaimae.ebankservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.*;
import net.chaimae.ebankservice.model.Customer;

import java.util.Date;
@Entity
@NoArgsConstructor@AllArgsConstructor@Builder
@Getter
@Setter
public class BankAccount {
    @Id
    private String id;
    private Date createAt;
    private double balance;
    private String customerId;
    private String type;
    @Transient
    private Customer customer;


    public void setCreatedAt(Date date) {
        this.createAt = date;
    }
}

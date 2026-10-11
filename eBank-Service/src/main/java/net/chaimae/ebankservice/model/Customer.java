package net.chaimae.ebankservice.model;

import lombok.*;

@Getter@Setter@AllArgsConstructor@NoArgsConstructor
@Builder
public class Customer {
    private Long id;
    private String name;
    private String email;
    public Customer(String id, String name) {
        this.id = Long.parseLong(String.valueOf(id));
        this.name = name;
    }
}

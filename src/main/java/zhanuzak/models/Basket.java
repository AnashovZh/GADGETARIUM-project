package zhanuzak.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "baskets")
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "basket_seg")
    @SequenceGenerator(name = "basket_seg", allocationSize = 1)
    private Long id;
    @OneToOne
    private User user;
    @ManyToMany
    private List<Product> products;

}
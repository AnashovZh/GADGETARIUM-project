package zhanuzak.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zhanuzak.enums.Category;
import zhanuzak.enums.Country;

import java.math.BigDecimal;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seg")
    @SequenceGenerator(name = "product_seg", allocationSize = 1)
    private Long id;
    @Column(unique = true)
    private String name;
    private BigDecimal price;
    @ElementCollection
    private List<String> images;
    private String characteristic;
    private boolean isFavorite;
    @Column(name = "made_in")
    @Enumerated(EnumType.STRING)
    private Country madeIn;
    @Enumerated(EnumType.STRING)
    private Category category;
    @ManyToOne(fetch = FetchType.EAGER)
    private Brand brand;
    @OneToMany(mappedBy = "product",cascade = {ALL})
    private List<Favorite> favorites;
    @ManyToMany(mappedBy = "products",cascade = ALL)
    private List<Basket> baskets;
    @OneToMany(mappedBy = "product")
    private List<Comment> comment;
}
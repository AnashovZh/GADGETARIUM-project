package zhanuzak.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zhanuzak.enums.Category;
import zhanuzak.enums.Country;
import zhanuzak.enums.Role;

import java.math.BigDecimal;
import java.util.List;


@AllArgsConstructor
@Getter
@Setter
@Entity
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
    @Column(name = "is_favorite")
    private boolean isFavorite;
    @Column(name = "made_in")
    @Enumerated(EnumType.STRING)
    private Country madeIn;
    @Enumerated(EnumType.STRING)
    private Category category;
    @ManyToOne(fetch = FetchType.EAGER)
    private Brand brand;
    @OneToMany(mappedBy = "product")
    private List<Favorite> favorite;
    @ManyToMany(mappedBy = "products")
    private List<Basket> basket;
    @OneToMany(mappedBy = "product")
    private List<Comment> comment;

    public Product() {
    }
}
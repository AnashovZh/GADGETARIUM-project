package zhanuzak.models;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "brands")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_seg")
    @SequenceGenerator(name = "brand_seg", allocationSize = 1)
    private Long id;
    @Column(name = "brand_name")
    private String brandName;
    private String image;
    @OneToMany(mappedBy = "brand")
    private List<Product> products;

}
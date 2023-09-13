package zhanuzak.models;

import jakarta.persistence.*;
import jdk.dynalink.linker.LinkerServices;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "favorites")
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favorite_seg")
    @SequenceGenerator(name = "favorite_seg", allocationSize = 1)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id  ")
    private User user;
    @ManyToOne
    private Product product;

}
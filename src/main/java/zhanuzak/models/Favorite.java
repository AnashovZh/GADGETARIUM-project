package zhanuzak.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.CascadeType.*;


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
    @ManyToOne(cascade = {REMOVE,REFRESH,MERGE,DETACH})
    @JoinColumn(name = "user_id  ")
    private User user;
    @ManyToOne(cascade = {REMOVE,REFRESH,MERGE,DETACH})
    private Product product;

}
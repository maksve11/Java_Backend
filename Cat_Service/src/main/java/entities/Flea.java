package entities;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name = "flea")
public class Flea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "flea_name", nullable = false)
    private String name;

    @ManyToOne(optional=false, cascade=CascadeType.ALL)
    @JoinColumn(name = "cat_id", referencedColumnName = "id")
    @NonNull
    private Cat cat;
}
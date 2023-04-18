package entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Table(name = "cat")
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "cat_name", nullable = false)
    private String name;

    @Column(name = "cat_birthdate")
    private LocalDate birthdate;

    @Column(name = "breed", nullable = false)
    private String breed;

    @Column(name = "color")
    private String color;

    @ManyToOne(optional=false, cascade=CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @NonNull
    private Owner owner;

    @OneToMany(fetch=FetchType.EAGER, mappedBy = "flea")
    @JoinColumn(name="flea_id")
    private List<Flea> fleas;

    public Cat(String name, LocalDate birthDate, Owner owner) {
        this.name = name;
        this.birthdate = birthDate;
        this.owner = owner;
    }
}
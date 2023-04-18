package entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "owner")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_name", nullable = false)
    private String name;

    @Column(name = "owner_birthdate", nullable = false)
    private LocalDate birthdate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch=FetchType.LAZY)
    private List<Cat> cats;

    public Owner(String name, LocalDate birthDate) {
        this.name = name;
        this.birthdate = birthDate;
        this.cats = new ArrayList<>();
    }
}
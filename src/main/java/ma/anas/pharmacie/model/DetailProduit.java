package ma.anas.pharmacie.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailProduit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fabricant;
    private String paysOrigine;
    private String composition;
    private String dosage;
    private String indications;

    @OneToOne(mappedBy = "detailProduit")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Produit produit;
}
package ma.anas.pharmacie.repository;

import ma.anas.pharmacie.model.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {}
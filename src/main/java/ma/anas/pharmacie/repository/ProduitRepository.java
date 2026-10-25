package ma.anas.pharmacie.repository;

import ma.anas.pharmacie.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<Produit, Long> {}
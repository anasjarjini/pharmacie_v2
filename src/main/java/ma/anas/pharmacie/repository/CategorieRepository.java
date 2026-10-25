package ma.anas.pharmacie.repository;

import ma.anas.pharmacie.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {}
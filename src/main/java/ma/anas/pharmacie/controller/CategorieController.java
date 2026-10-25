package ma.anas.pharmacie.controller;

import lombok.RequiredArgsConstructor;
import ma.anas.pharmacie.model.Categorie;
import ma.anas.pharmacie.repository.CategorieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategorieController {

    private final CategorieRepository categorieRepository;

    @GetMapping
    public List<Categorie> lister() {
        return categorieRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categorie> detail(@PathVariable Long id) {
        return categorieRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Categorie> ajouter(@RequestBody Categorie categorie) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categorieRepository.save(categorie));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categorie> modifier(@PathVariable Long id, @RequestBody Categorie body) {
        return categorieRepository.findById(id).map(existing -> {
            existing.setNom(body.getNom());
            existing.setDescription(body.getDescription());
            return ResponseEntity.ok(categorieRepository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        if (!categorieRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categorieRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

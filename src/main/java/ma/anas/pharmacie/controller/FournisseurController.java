package ma.anas.pharmacie.controller;

import lombok.RequiredArgsConstructor;
import ma.anas.pharmacie.model.Fournisseur;
import ma.anas.pharmacie.repository.FournisseurRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fournisseurs")
@RequiredArgsConstructor
public class FournisseurController {

    private final FournisseurRepository fournisseurRepository;

    @GetMapping
    public List<Fournisseur> lister() {
        return fournisseurRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fournisseur> detail(@PathVariable Long id) {
        return fournisseurRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Fournisseur> ajouter(@RequestBody Fournisseur fournisseur) {
        return ResponseEntity.status(HttpStatus.CREATED).body(fournisseurRepository.save(fournisseur));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fournisseur> modifier(@PathVariable Long id, @RequestBody Fournisseur body) {
        return fournisseurRepository.findById(id).map(existing -> {
            existing.setNom(body.getNom());
            existing.setContact(body.getContact());
            existing.setAdresse(body.getAdresse());
            return ResponseEntity.ok(fournisseurRepository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        if (!fournisseurRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        fournisseurRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

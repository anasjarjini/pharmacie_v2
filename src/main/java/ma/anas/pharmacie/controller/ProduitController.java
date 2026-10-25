package ma.anas.pharmacie.controller;

import lombok.RequiredArgsConstructor;
import ma.anas.pharmacie.model.DetailProduit;
import ma.anas.pharmacie.model.Produit;
import ma.anas.pharmacie.repository.ProduitRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
@RequiredArgsConstructor
public class ProduitController {

    private final ProduitRepository produitRepository;

    @GetMapping
    public List<Produit> lister() {
        return produitRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produit> detail(@PathVariable Long id) {
        return produitRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Produit> ajouter(@RequestBody Produit produit) {
        Produit saved = produitRepository.save(produit);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produit> modifier(@PathVariable Long id, @RequestBody Produit body) {
        return produitRepository.findById(id).map(existing -> {
            existing.setNom(body.getNom());
            existing.setDescription(body.getDescription());
            existing.setPrix(body.getPrix());
            existing.setCodeBarre(body.getCodeBarre());
            existing.setDateExpiration(body.getDateExpiration());
            existing.setCategorie(body.getCategorie());
            existing.setFournisseur(body.getFournisseur());
            existing.setTags(body.getTags());

            DetailProduit detailExistant = existing.getDetailProduit();
            DetailProduit detailBody = body.getDetailProduit();
            if (detailExistant != null && detailBody != null) {
                detailExistant.setFabricant(detailBody.getFabricant());
                detailExistant.setPaysOrigine(detailBody.getPaysOrigine());
                detailExistant.setComposition(detailBody.getComposition());
                detailExistant.setDosage(detailBody.getDosage());
                detailExistant.setIndications(detailBody.getIndications());
            } else {
                existing.setDetailProduit(detailBody);
            }
            return ResponseEntity.ok(produitRepository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimer(@PathVariable Long id) {
        if (!produitRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        produitRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

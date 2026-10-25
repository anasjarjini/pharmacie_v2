package ma.anas.pharmacie.controller;

import lombok.RequiredArgsConstructor;
import ma.anas.pharmacie.model.Produit;
import ma.anas.pharmacie.repository.ProduitRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/produits")
@RequiredArgsConstructor
public class ProduitViewController {

    private final ProduitRepository produitRepository;

    @GetMapping
    public String liste(Model model) {
        model.addAttribute("produits", produitRepository.findAll());
        return "produits/liste";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("produit", produit);
        return "produits/detail";
    }
}

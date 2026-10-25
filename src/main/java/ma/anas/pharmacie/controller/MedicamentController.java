package ma.anas.pharmacie.controller;

import lombok.RequiredArgsConstructor;
import ma.anas.pharmacie.repository.ProduitRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/medicaments")
@RequiredArgsConstructor
public class MedicamentController {
    private final ProduitRepository repo;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("produits", repo.findAll());
        return "medicaments";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/medicaments";
    }
}
package ma.anas.pharmacie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VenteController {
    @GetMapping("/ventes")
    public String ventes() { return "ventes"; }
}
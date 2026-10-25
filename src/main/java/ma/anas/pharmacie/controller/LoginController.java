
package ma.anas.pharmacie.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")         public String login()  { return "login"; }
    @GetMapping({"/", "/home"})   public String home()   { return "home"; }
    @GetMapping("/access-denied") public String denied() { return "access-denied"; }
}
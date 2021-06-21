package dev.mikoto2000.study.springboot.keycloak.gettingstarted;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * AppController
 */
@Controller
public class AppController {
    @GetMapping(path = "/")
    public String index(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());

        return "index";
    }

    @GetMapping(path = "/users/{name}")
    public String customers(@PathVariable("name") String name, Principal principal, Model model) {

        model.addAttribute("username", name);
        return "userpage";
    }
}

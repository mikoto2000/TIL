package dev.mikoto2000.study.springboot.tomcat.war.firststep.controller;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class IndexController {

    private final Environment environment;

    public IndexController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        String[] activeProfiles = environment.getActiveProfiles();
        List<String> profiles = activeProfiles.length > 0
                ? Arrays.asList(activeProfiles)
                : Arrays.asList(environment.getDefaultProfiles());
        model.addAttribute("activeProfiles", profiles);
        return "index";
    }
}
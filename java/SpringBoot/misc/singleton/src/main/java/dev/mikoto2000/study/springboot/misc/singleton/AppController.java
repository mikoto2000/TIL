package dev.mikoto2000.study.springboot.misc.singleton;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AppController {

    private ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

    @GetMapping(path = "/")
    public String index(Model model) {
        model.addAttribute("map", map);

        return "index";
    }

    @GetMapping(path = "/{key}/{value}")
    public String index(@PathVariable String key, @PathVariable String value, Model model) {
        map.put(key, value);
        model.addAttribute("map", map);

        return "index";
    }

}

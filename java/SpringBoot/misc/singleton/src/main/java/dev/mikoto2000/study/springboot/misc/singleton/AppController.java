package dev.mikoto2000.study.springboot.misc.singleton;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AppController {

    private static final int CONCURRENCY_LEVEL = 1;
    private static final int MAXIMUM_SIZE = 10;
    private static final int EXPIRE_AFTER_WRITE = 10;

    private Cache<String, String> map = CacheBuilder.newBuilder()
            .concurrencyLevel(CONCURRENCY_LEVEL)
            .maximumSize(MAXIMUM_SIZE)
            .expireAfterWrite(EXPIRE_AFTER_WRITE, TimeUnit.SECONDS)
            .<String, String>build();

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

package echo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * EchoController
 */
@RestController
public class EchoController {
    @RequestMapping("/")
    public String index() {
        return "`/echo?message=ECHO_MESSAGE` の形でリクエストを出してください。";
    }

    @RequestMapping(method = {GET, POST}, path = "/echo")
    public EchoResponse echo(@RequestParam(value="message", defaultValue="DEFAULT") String message) {
        return new EchoResponse("1", message);
    }
}


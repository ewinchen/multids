package dev.ewin.multids.controller.api;

//import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DefaultApiController {

    private Logger logger = LoggerFactory.getLogger("com.esquel.gek.prototype.controller.api.DefaultApiController");
}

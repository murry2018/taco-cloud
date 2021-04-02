package sia.tacocloud;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import lombok.extern.slf4j.Slf4j;

// memo : https://stackoverflow.com/a/59165297
// trouble shooting when occured 404
// Is my controller mapped correctly? -> find out mapping list logged by `handleContextRefresh` event listener
// If mapped -> check if corresponding template is created in */target/classes/templates* and doesn't have any error.
// If not mapped -> check if the controller is successfully created in */target/classes/packagename/classname*
//                  and its creation time is past of that for source file.
@Slf4j
@Configuration
public class Printer {
    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        context.getBean(RequestMappingHandlerMapping.class)
            .getHandlerMethods().forEach((key, value) -> log.info("{} {}", key, value));
    }
}

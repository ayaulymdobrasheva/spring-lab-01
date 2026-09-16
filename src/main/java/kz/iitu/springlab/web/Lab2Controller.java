package kz.iitu.springlab.web;
import kz.iitu.springlab.scope.ScopeService;
import kz.iitu.springlab.notify.NotificationService;
import org.springframework.web.bind.annotation.*;
import kz.iitu.springlab.lifecycle.LifecycleDemo;
import java.util.Map;

@RestController
@RequestMapping("/api/lab2")
public class Lab2Controller {

    private final NotificationService notifications;
    private final LifecycleDemo lifecycle;
    private final ScopeService scopes;

    public Lab2Controller(NotificationService notifications,
                          LifecycleDemo lifecycle,
                          ScopeService scopes) {
        this.notifications = notifications;
        this.lifecycle = lifecycle;
        this.scopes = scopes;
    }

    @GetMapping("/notify")
    public Map<String, Object> notify(
            @RequestParam(defaultValue = "Hello") String text) {

        return Map.of(
                "primary", notifications.viaPrimary(text),
                "console", notifications.viaConsole(text),
                "all", notifications.viaAll(text),
                "beanNames", notifications.names()
        );
    }

    @GetMapping("/lifecycle")
    public Object lifecycle() {
        return lifecycle.events();
    }


@GetMapping("/scopes")
public Map<String, Object> scopes() {
    return Map.of(
            "singleton", scopes.singletonIds(),
            "prototype", scopes.prototypeIds()
    );
}
    @GetMapping("/custom")
    public Map<String, Object> custom(
            @RequestParam(defaultValue = "Hello123") String text) {

        return Map.of(
                "input", text,
                "result", notifications.viaMasking(text)
        );
    }
}
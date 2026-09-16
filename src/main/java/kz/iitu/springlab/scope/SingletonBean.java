package kz.iitu.springlab.scope;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SingletonBean {

    private final String id = UUID.randomUUID().toString();

    public String getId() {
        return id;
    }
}
package kz.iitu.springlab.scope;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScopeService {

    private final SingletonBean singleton;
    private final ObjectProvider<PrototypeBean> prototypes;

    public ScopeService(
            SingletonBean singleton,
            ObjectProvider<PrototypeBean> prototypes) {

        this.singleton = singleton;
        this.prototypes = prototypes;
    }

    public List<String> singletonIds() {
        return List.of(
                singleton.getId(),
                singleton.getId()
        );
    }

    public List<String> prototypeIds() {
        return List.of(
                prototypes.getObject().getId(),
                prototypes.getObject().getId()
        );
    }
}
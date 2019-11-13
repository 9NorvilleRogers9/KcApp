package org.keycloak.services.resources;

import org.jboss.logging.Logger;
import org.jboss.resteasy.core.Dispatcher;
import org.keycloak.services.resources.KeycloakApplication;
import org.keycloak.services.resources.WelcomeResource;
import org.keycloak.services.resources.WelcomeResource1;

import javax.servlet.ServletContext;
import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.core.Context;
import java.util.concurrent.atomic.AtomicBoolean;

public class KeycloakApplication1 extends KeycloakApplication {

    private static final Logger logger = Logger.getLogger(KeycloakApplication.class);

    public KeycloakApplication1(@Context ServletContext context,@Context Dispatcher dispatcher) {

        super(context ,dispatcher);
        for (Object o : singletons) {
            if (o instanceof WelcomeResource) {
                singletons.remove(o);
                break;

            }
        }
        AtomicBoolean bootstrapAdminUser = new AtomicBoolean(false);
        singletons.add(new WelcomeResource1(bootstrapAdminUser.get()));

    }
}
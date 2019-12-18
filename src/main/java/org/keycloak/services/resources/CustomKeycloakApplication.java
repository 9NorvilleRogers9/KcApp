package org.keycloak.services.resources;

import entities.SettingsEntity;
import org.jboss.logging.Logger;
import org.jboss.resteasy.core.Dispatcher;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionTask;
import org.keycloak.models.utils.KeycloakModelUtils;
import org.keycloak.services.managers.ApplianceBootstrap;
import representations.SettingsRepresentation;
import spi.SettingsService;
//import java.util.concurrent.atomic;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomKeycloakApplication extends KeycloakApplication {

    private static final Logger logger = Logger.getLogger(KeycloakApplication.class);

    public CustomKeycloakApplication(@Context ServletContext context, @Context Dispatcher dispatcher) {
        super(context, dispatcher);

        Set<Object> mySingletons = new HashSet<Object>();

        for (Object o : singletons) {
            if (o instanceof WelcomeResource) {
                AtomicBoolean marker = getSettings();
                mySingletons.add(new CustomWelcomeResource(marker.get()));
            } else {
                mySingletons.add(o);
            }
        }
        singletons = mySingletons;
    }

    private AtomicBoolean getSettings() {
        final AtomicBoolean bootstrapAdminUser = new AtomicBoolean(false);

        KeycloakModelUtils.runJobInTransaction(sessionFactory, new KeycloakSessionTask() {
            @Override
            public void run(KeycloakSession session) {
                boolean shouldBootstrapAdmin = new ApplianceBootstrap(session).isNoMasterUser();
                final String key="WasTheFirstRegistration";
                SettingsRepresentation sr = session.getProvider(SettingsService.class).findSettings(key);
                if((sr==null) && (shouldBootstrapAdmin == true))
                {
                    bootstrapAdminUser.set(shouldBootstrapAdmin);
                    return;
                }
                if ((sr!=null) && (sr.getValue().equals("true")) )
                {
                    bootstrapAdminUser.set(false);
                }
                if((sr==null) && (shouldBootstrapAdmin == false))
                {
                    final String value ="true";
                    SettingsRepresentation settings = new SettingsRepresentation(new SettingsEntity());
                    settings.setKey(key);
                    settings.setValue(value);
                    session.getProvider(SettingsService.class).addSettings(settings);
                }
            }
        });
        return bootstrapAdminUser;

    }

}





















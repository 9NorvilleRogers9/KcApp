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
import spi.SettingsServiceProviderFactory;
import spi.impl.SettingsServiceProviderFactoryImpl;
//import java.util.concurrent.atomic;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomKeycloakApplication extends KeycloakApplication {

    private static final Logger logger = Logger.getLogger(KeycloakApplication.class);

    private KeycloakSession sessions;
    SettingsEntity settingsEntity = new SettingsEntity();
    SettingsRepresentation sr = new SettingsRepresentation(settingsEntity);

    public CustomKeycloakApplication(@Context ServletContext context, @Context Dispatcher dispatcher) {
        super(context, dispatcher);

        Set<Object> mySingletons = new HashSet<Object>();

        for (Object o : singletons) {
            if (o instanceof WelcomeResource) {
                AtomicBoolean marker = getSettings();
                mySingletons.add(new CustomWelcomeResource(marker.get(), sessions, sr));
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
                    bootstrapAdminUser.set(shouldBootstrapAdmin);
                    sessions = session;
                }

            });
        sr = sessions.getProvider(SettingsService.class).findSettings("CustomWelcomeResource");
        if ((sr.getValue() == "true") && (sr!=null))
        {
            return bootstrapAdminUser;
        }

            return bootstrapAdminUser;

        }
}

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
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class CustomKeycloakApplication extends KeycloakApplication implements Constants {

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

                SettingsRepresentation sr = session.getProvider(SettingsService.class).findSettings(WasTheFirstRegistration);
                if(sr==null)
                {
					boolean shouldBootstrapAdmin = new ApplianceBootstrap(session).isNoMasterUser();
                    bootstrapAdminUser.set(shouldBootstrapAdmin);
                    if(shouldBootstrapAdmin == false)
						{
							SettingsRepresentation settings = new SettingsRepresentation(new SettingsEntity());
							settings.setKey(WasTheFirstRegistration);
							settings.setValue(TheFirstRegistrationValue );
							session.getProvider(SettingsService.class).addSettings(settings);
						}
                }
				else 
				{
					 bootstrapAdminUser.set(!Boolean.valueOf(sr.getValue()));
				}
            }
        });
        return bootstrapAdminUser;

    }

}





















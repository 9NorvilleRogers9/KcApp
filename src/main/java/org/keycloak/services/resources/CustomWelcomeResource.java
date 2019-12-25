package org.keycloak.services.resources;
import entities.SettingsEntity;
import org.keycloak.models.KeycloakSession;
import representations.SettingsRepresentation;
import spi.SettingsService;
import javax.ws.rs.Path;
import javax.ws.rs.core.*;

@Path("/")
public class CustomWelcomeResource extends WelcomeResource implements Constants {

    @Context
    private KeycloakSession session ;

    private boolean bootstrap;

    public CustomWelcomeResource(boolean bootstrap) {

        super(bootstrap);
        this.bootstrap=bootstrap;
    }

    @Override
    public Response createUser(MultivaluedMap<String, String> formData) {

        checkBootstrap(bootstrap);
        return super.createUser(formData);
    }

    private void checkBootstrap(boolean bootstrap)
    {
        if(bootstrap==true)
        {
            setSettings();
        }
    }

   private void setSettings() {
       String value = Boolean.toString(bootstrap);
       SettingsRepresentation settings = new SettingsRepresentation(new SettingsEntity());
       settings.setKey(WasTheFirstRegistration);
       settings.setValue(value);
       session.getProvider(SettingsService.class).addSettings(settings);
   }
}


















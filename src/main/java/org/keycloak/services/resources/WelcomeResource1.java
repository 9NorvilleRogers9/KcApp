package org.keycloak.services.resources;

import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.models.KeycloakSession;
import representations.SettingsRepresentation;
import spi.SettingsService;


import javax.persistence.EntityManager;
import javax.ws.rs.Path;
import javax.ws.rs.core.*;

@Path("/")
public class WelcomeResource1 extends WelcomeResource {


    private static final String KEYCLOAK_STATE_CHECKER = "WELCOME_STATE_CHECKER";
    @Context
    private KeycloakSession session ;

    @Context
    protected HttpHeaders headers;

    private boolean bootstrap;

    private SettingsRepresentation sr;

    public WelcomeResource1(boolean bootstrap, KeycloakSession session , SettingsRepresentation sr) {

        super(bootstrap);
        this.bootstrap=bootstrap;
        this.session=session;
        this.sr=sr;

    }


    @Override
    public Response createUser(MultivaluedMap<String, String> formData) {

        checkBootstrap(bootstrap);
        return super.createUser(formData);
    }

    private  void checkBootstrap(boolean bootstrap)
    {
        if(bootstrap==true)
        {
            setSettings(sr);
        }
    }
   /* private void setSettings()
    {
        try (FileOutputStream outFile = new FileOutputStream("D://NetCracker//Keycloak//keycloak-7.0.0//keycloak-7.0.0//FirstAdminValidation.txt"))
        {

            String text = "1";
            byte[] buffer = text.getBytes();
            outFile.write(buffer, 0, buffer.length);
        }

        catch(IOException ex)
        {
            logger.warn("File not found", ex);
        }
    }*/

    private void setSettings(SettingsRepresentation settings) {
        session.getProvider(SettingsService.class).addSettings(settings);
    }
}

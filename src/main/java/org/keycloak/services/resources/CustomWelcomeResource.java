package org.keycloak.services.resources;
import org.keycloak.models.KeycloakSession;
import representations.SettingsRepresentation;
import spi.SettingsService;
import javax.ws.rs.Path;
import javax.ws.rs.core.*;

@Path("/")
public class CustomWelcomeResource extends WelcomeResource {


    private static final String KEYCLOAK_STATE_CHECKER = "WELCOME_STATE_CHECKER";
    @Context
    private KeycloakSession session ;

    @Context
    protected HttpHeaders headers;

    private boolean bootstrap;

    private SettingsRepresentation sr;

    public CustomWelcomeResource(boolean bootstrap/*, KeycloakSession session*/ , SettingsRepresentation sr) {

        super(bootstrap);
        this.bootstrap=bootstrap;
        //this.session=session;
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

    private void setSettings(SettingsRepresentation settings) {
        final  String currentClass = "CustomWelcomeResource";
        String value = Boolean.toString(bootstrap);
        settings.setKey(currentClass);
        settings.setValue(value);
        session.getProvider(SettingsService.class).addSettings(settings);
    }
}
package org.keycloak.services.resources;

import org.keycloak.common.ClientConnection;
import org.keycloak.common.Version;
import org.keycloak.common.util.Base64Url;
import org.keycloak.models.BrowserSecurityHeaders;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.utils.KeycloakModelUtils;
import org.keycloak.services.ForbiddenException;
import org.keycloak.services.ServicesLogger;
import org.keycloak.services.Urls;
import org.keycloak.services.managers.ApplianceBootstrap;
import org.keycloak.services.util.CacheControlUtil;
import org.keycloak.services.util.CookieHelper;
import org.keycloak.theme.BrowserSecurityHeaderSetup;
import org.keycloak.theme.FreeMarkerUtil;
import org.keycloak.theme.Theme;
import representations.SettingsRepresentation;
import spi.SettingsService;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class WelcomeResource1 extends WelcomeResource {


    private static final String KEYCLOAK_STATE_CHECKER = "WELCOME_STATE_CHECKER";
    @Context
    private KeycloakSession session;

    @Context
    protected HttpHeaders headers;

    private boolean bootstrap;

    private SettingsRepresentation sr;

    public WelcomeResource1(boolean bootstrap) {

        super(bootstrap);
        this.bootstrap=bootstrap;

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
            setSettings();
        }
    }
    private void setSettings()
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
    }

   // private void setSettings(SettingsRepresentation sr)
    {
        session.getProvider(SettingsService.class).addSettings(sr);
    }


}

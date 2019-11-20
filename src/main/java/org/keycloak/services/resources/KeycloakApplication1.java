package org.keycloak.services.resources;

import org.apache.commons.io.FileUtils;
import org.jboss.logging.Logger;
import org.jboss.resteasy.core.Dispatcher;
import org.keycloak.exportimport.ExportImportManager;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionTask;
import org.keycloak.models.dblock.DBLockManager;
import org.keycloak.models.dblock.DBLockProvider;
import org.keycloak.models.utils.KeycloakModelUtils;
import org.keycloak.services.managers.ApplianceBootstrap;
import org.keycloak.services.resources.KeycloakApplication;
import org.keycloak.services.resources.WelcomeResource;
import org.keycloak.services.resources.WelcomeResource1;

//import java.util.concurrent.atomic;
import javax.servlet.ServletContext;
import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.core.Context;
import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public class KeycloakApplication1 extends KeycloakApplication {

    private static final Logger logger = Logger.getLogger(KeycloakApplication.class);
    protected Set<Object> mySingletons = new HashSet<Object>();

    private boolean marker;
    private boolean registrationMarker=false;
    private boolean emptyFile=false;

    public KeycloakApplication1(@Context ServletContext context,@Context Dispatcher dispatcher) {
        super(context ,dispatcher);
            final AtomicBoolean bootstrapAdminUser = new AtomicBoolean(false);
            KeycloakModelUtils.runJobInTransaction(sessionFactory, new KeycloakSessionTask() {
                @Override
                public void run(KeycloakSession session) {
                    boolean shouldBootstrapAdmin = new ApplianceBootstrap(session).isNoMasterUser();
                    bootstrapAdminUser.set(shouldBootstrapAdmin);
                }

            });

        for(Object o:singletons) {
            if(!(o instanceof WelcomeResource))
            {
                mySingletons.add(o);
            }
            else{
                firstUserRegistrationReader();
                checkFile();
                marker=bootstrapAdminUser.get();
                if ((marker==true) && (registrationMarker==true)) {
                    mySingletons.add(new WelcomeResource1(false));
                    singletons=mySingletons;}
                if((marker==true)&&(registrationMarker==false)&&(emptyFile==true)) {
                    mySingletons.add(new WelcomeResource1(true));
                    singletons=mySingletons;
                }
            }
        }
    }

    private  void checkFile()
    {
        try(FileInputStream inFile = new FileInputStream("D://NetCracker//Keycloak//keycloak-7.0.0//keycloak-7.0.0//FirstAdminValidation.txt")) {

            int b = inFile.read();
            if (b == -1)
            {
                emptyFile=true;
            }

        }
        catch(IOException ex)
        {
            logger.warn("File not found", ex);
        }
    }

    private  void firstUserRegistrationReader()
    {
        try(FileInputStream inFile = new FileInputStream("D://NetCracker//Keycloak//keycloak-7.0.0//keycloak-7.0.0//FirstAdminValidation.txt"))
        {
            int i=-1;
            while((i=inFile.read())!=-1){

                if(i=='1')
                {
                    registrationMarker=true;
                }
            }
        }

        catch(IOException ex)
        {
            logger.warn("File not found", ex);
        }
    }

}
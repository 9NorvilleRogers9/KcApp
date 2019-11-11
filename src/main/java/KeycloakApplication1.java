import org.jboss.logging.Logger;
//import org.keycloak.common.util.Resteasy;
import org.keycloak.exportimport.ExportImportManager;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.models.KeycloakSessionTask;
import org.keycloak.models.dblock.DBLockManager;
import org.keycloak.models.dblock.DBLockProvider;
import org.keycloak.models.utils.KeycloakModelUtils;
import org.keycloak.models.utils.PostMigrationEvent;
import org.keycloak.services.error.KeycloakErrorHandler;
import org.keycloak.services.filters.KeycloakTransactionCommitter;
import org.keycloak.services.managers.ApplianceBootstrap;
import org.keycloak.services.resources.admin.AdminRoot;
import org.keycloak.services.util.ObjectMapperResolver;

import javax.servlet.ServletContext;
import java.util.concurrent.atomic.AtomicBoolean;

public class KeycloakApplication1 extends KeycloakApplication {


    private static final Logger logger = Logger.getLogger(KeycloakApplication1.class);

    public KeycloakApplication1() {
        super();
        for (Object o : singletons) {
            if (o instanceof WelcomeResource) {
                singletons.remove(o);
            }
        }
        AtomicBoolean bootstrapAdminUser = new AtomicBoolean(false);
        singletons.add(new WelcomeResource1(bootstrapAdminUser.get()));

    }
}
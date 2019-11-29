package spi;

import entities.SettingsEntity;
import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.utils.KeycloakModelUtils;
import representations.SettingsRepresentation;


import javax.persistence.EntityManager;
import java.util.List;

public class SettingsService implements Service {


    private final KeycloakSession session;

    public SettingsService(KeycloakSession session) {
        this.session = session;
        if (getRealm() == null) {
            throw new IllegalStateException("The service cannot accept a session without a realm in its context.");
        }
    }

    @Override
    public SettingsRepresentation findSettings(String key) {
        return null;
    }

    private EntityManager getEntityManager() {
        return session.getProvider(JpaConnectionProvider.class).getEntityManager();
    }

    protected RealmModel getRealm() {
        return session.getContext().getRealm();
    }

    @Override
    public SettingsRepresentation addSettings(SettingsRepresentation settings) {
        SettingsEntity entity = new SettingsEntity();
        /*String key = settings.getKey()==null ?  KeycloakModelUtils.generateId() : settings.getKey();
        entity.setKey(key);
        entity.setValue("true");*/
        getEntityManager().persist(entity);

        return settings;
    }

    @Override
    public void close() {

    }

}

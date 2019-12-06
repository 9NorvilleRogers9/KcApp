package spi.impl;

import entities.SettingsEntity;
import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.models.KeycloakSession;
import representations.SettingsRepresentation;
import spi.SettingsService;

import javax.persistence.EntityManager;


public class SettingsServiceImpl implements SettingsService {

    private final KeycloakSession session;

    public SettingsServiceImpl(KeycloakSession session) {
        this.session = session;

    }

    private EntityManager getEntityManager() {
        return session.getProvider(JpaConnectionProvider.class).getEntityManager();
    }

    @Override
    public SettingsRepresentation findSettings(String key) {
        SettingsEntity entity = getEntityManager().find(SettingsEntity.class, key);
        return entity==null ? null : new SettingsRepresentation(entity);
    }

    @Override
    public SettingsRepresentation addSettings(SettingsRepresentation settings) {
        SettingsEntity entity = new SettingsEntity();
        entity.setKey("KeycloakSettings");
        entity.setValue("true");
        getEntityManager().persist(entity);
        return settings;
    }

    @Override
    public void close() {

    }

}

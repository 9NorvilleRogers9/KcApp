package spi.impl;

import entities.SettingsEntity;

import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.models.KeycloakSession;
import representations.SettingsRepresentation;
import spi.SettingsService;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

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
        SettingsEntity entity=findByKey(key);
        return entity==null ? null : new SettingsRepresentation(entity);
    }

    @Override
    public void addSettings(SettingsRepresentation settings) {
        SettingsEntity entity = new SettingsEntity();
        entity.setKey(settings.getKey());
        entity.setValue(settings.getValue());
        getEntityManager().persist(entity);
    }

    private SettingsEntity findByKey(String key) {
        try {
            SettingsEntity settingsEntity = getEntityManager().createNamedQuery("findKey", SettingsEntity.class)
                    .setParameter("key", key)
                    .getSingleResult();
            return settingsEntity;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void close() {
    }
}

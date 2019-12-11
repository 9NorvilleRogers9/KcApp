package spi.impl;

import entities.SettingsEntity;


import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.models.KeycloakSession;
import representations.SettingsRepresentation;
import spi.SettingsService;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.ws.rs.NotFoundException;
import java.util.LinkedList;
import java.util.List;


public class SettingsServiceImpl implements SettingsService {

    private final KeycloakSession session;

    public SettingsServiceImpl(KeycloakSession session) {
        this.session = session;

    }

    private EntityManager getEntityManager() {
        return session.getProvider(JpaConnectionProvider.class).getEntityManager();
    }
    @Override
    public List<SettingsRepresentation> listSettings(SettingsRepresentation settingsRepresentation) {
        List<SettingsEntity> companyEntities = session.getProvider(JpaConnectionProvider.class).getEntityManager().createNamedQuery("findKey", SettingsEntity.class)
                .setParameter("key","CustomWelcomeResource")
                .getResultList();
        List<SettingsRepresentation> result = new LinkedList<>();
        for (SettingsEntity entity : companyEntities) {
            result.add(new SettingsRepresentation(entity));
        }
        return result;
    }

    /*@Override
    public SettingsRepresentation findSettings(String key) {
        SettingsEntity entity= getEntityManager().find(SettingsEntity.class, key);
        //session.getProvider(JpaConnectionProvider.class).getEntityManager().createNamedQuery("from SettingsEntity  where key = :key",SettingsEntity.class).setParameter("key",settingsRepresentation.getKey()).getResultList();
        return entity==null ? null : new SettingsRepresentation(entity);
    }*/
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

            SettingsEntity settingsEntity = getEntityManager().createNamedQuery("findKey", SettingsEntity.class)
                    .setParameter("key", key)
                    .getSingleResult();
            return settingsEntity;


    }

    @Override
    public void close() {
    }
}

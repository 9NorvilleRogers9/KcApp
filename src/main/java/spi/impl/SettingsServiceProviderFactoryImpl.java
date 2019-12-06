package spi.impl;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import spi.SettingsService;
import spi.SettingsServiceProviderFactory;
import spi.impl.SettingsServiceImpl;

public class SettingsServiceProviderFactoryImpl implements SettingsServiceProviderFactory {

    @Override
    public SettingsService create(KeycloakSession keycloakSession) {

        return new SettingsServiceImpl(keycloakSession);
    }

    @Override
    public void init(Config.Scope scope) {

    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {

        return "settingsServiceImpl";
    }

}

package spi;

import org.keycloak.provider.Provider;
import org.keycloak.provider.ProviderFactory;
import org.keycloak.provider.Spi;

public class SettingsSpi implements Spi {
    @Override
    public boolean isInternal() {
        return false;
    }

    @Override
    public String getName() {

        return "settings";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {

        return Service.class;
    }

    @Override
    public Class<? extends ProviderFactory> getProviderFactoryClass() {

        return ServiceProviderFactory.class;
    }
}

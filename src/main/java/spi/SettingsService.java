package spi;

import org.keycloak.models.KeycloakSession;
import org.keycloak.provider.Provider;
import representations.SettingsRepresentation;
import java.util.List;

public interface SettingsService extends Provider {

    SettingsRepresentation findSettings(String key);

    void addSettings(SettingsRepresentation settings);

}

package spi;

import org.keycloak.models.KeycloakSession;
import org.keycloak.provider.Provider;
import representations.SettingsRepresentation;
import java.util.List;

public interface SettingsService extends Provider {

    //List<SettingsRepresentation> listSettings();

    SettingsRepresentation findSettings(String key);

    SettingsRepresentation addSettings(SettingsRepresentation settings);




}

package spi;

import org.keycloak.provider.Provider;
import representations.SettingsRepresentation;
import java.util.List;

public interface Service extends Provider {

    //List<SettingsRepresentation> listSettings();

    SettingsRepresentation findSettings(String key);

    SettingsRepresentation addSettings(SettingsRepresentation settings);


}

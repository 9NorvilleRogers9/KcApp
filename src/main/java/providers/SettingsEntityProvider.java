package providers;

import entities.SettingsEntity;
import org.keycloak.connections.jpa.entityprovider.JpaEntityProvider;


import java.util.Collections;
import java.util.List;

public class SettingsEntityProvider implements JpaEntityProvider {


    @Override
    public List<Class<?>> getEntities() {
       return  Collections.<Class<?>>singletonList(SettingsEntity.class);
    }

    @Override
    public String getChangelogLocation() {
        return "META-INF/settings-changelog.xml";
    }

    @Override
    public String getFactoryId() {
        return SettingsEntityProviderFactory.ID;
    }

    @Override
    public void close() {

    }
}

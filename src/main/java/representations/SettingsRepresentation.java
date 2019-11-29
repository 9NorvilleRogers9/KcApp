package representations;

import entities.SettingsEntity;
public class SettingsRepresentation {

    String key;
    String value;

    public SettingsRepresentation(SettingsEntity settingsEntity) {

        key = settingsEntity.getKey();
        value = settingsEntity.getValue();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

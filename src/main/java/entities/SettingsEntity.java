package entities;

import org.keycloak.models.jpa.entities.UserEntity;

import javax.persistence.*;

import javax.persistence.Entity;

@Entity
@Table(name = "SETTINGS")
//@NamedQuery(name = "findKey", query = "SELECT b FROM SettingsEntity b WHERE  b.key = :key")
@NamedQueries({ @NamedQuery(name = "findKey", query = "from SettingsEntity  where key = :key") })
public class SettingsEntity {

    @Id
    @Column(name = "key", nullable = false)
    protected String key;

    @Column(name = "value",nullable = false)
    private  String value ;

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


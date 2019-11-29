package entities;

import org.keycloak.models.jpa.entities.UserEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;

@Entity
@Table(name = "SETTINGS-ENTITY")
public class SettingsEntity {

    @Id
    @Column(name = "KEY", length = 36)
    @Access(AccessType.PROPERTY)
    protected String key;

    @Column(name = "VALUE")
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


package tech.ada.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.NotBlank;
@Entity
public class Accessory extends PanacheEntityBase {
    @Id
    @GeneratedValue
    private Long id;

    public Accessory(@NotBlank String name) {
    }

    public Accessory() {

    }

    public static Object ListAll() {
        return null;
    }

    public static Accessory findById(Long id) {
        return null;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

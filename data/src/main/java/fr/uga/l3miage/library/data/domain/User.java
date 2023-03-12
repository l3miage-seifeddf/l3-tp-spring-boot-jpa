package fr.uga.l3miage.library.data.domain;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

// Attention le mot 'user' est reservé
@Entity
@DiscriminatorValue("USERS")
@Table(name = "`User`")
public class User extends Person {

    @Temporal(TemporalType.DATE)
    private Date registered;

    @Column(name = "late_ratio")
    private float lateRatio;

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public float getLateRatio() {
        return lateRatio;
    }

    public void setLateRatio(float lateRatio) {
        this.lateRatio = lateRatio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        if (!super.equals(o)) return false;
        return Float.compare(user.lateRatio, lateRatio) == 0 && Objects.equals(registered, user.registered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), registered, lateRatio);
    }
}

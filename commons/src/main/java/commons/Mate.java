package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Mate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String lastname;
    private boolean hj;

    public Mate() {
        name = "";
        lastname = "";
        hj = false;
    }

    public Mate(String name, String lastname, boolean hj) {
        this.name = name;
        this.lastname = lastname;
        this.hj = hj;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public boolean isHj() {
        return hj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mate mate = (Mate) o;

        return new EqualsBuilder().append(id, mate.id)
                .append(hj, mate.hj)
                .append(name, mate.name)
                .append(lastname, mate.lastname)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(lastname)
                .append(hj)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("lastname", lastname)
                .append("hj", hj)
                .toString();
    }
}

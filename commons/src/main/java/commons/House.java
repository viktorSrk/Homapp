package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany(mappedBy = "house", orphanRemoval = true)
    private final java.util.List<Mate> mates;
    private int size;

    public House() {
        mates = new ArrayList<>();
        size = 10;
    }

    public House(int houseSize) {
        mates = new ArrayList<>();
        size = houseSize;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public java.util.List<Mate> getMates() {
        return mates;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Change a Mate of a specific room
     * @param room room number which has a change in it
     * @param mate the new Mate that replaces the old
     */
    public void changeMate(int room, Mate mate) {
        if (room > size) new IndexOutOfBoundsException("Room number is too big for the size of the House");
        if (room <= 0) new IndexOutOfBoundsException("There is no room number 0 or lower");
        mates.add(room - 1, mate);
    }

    /**
     * Gets a Mate from a certain room number
     * @param room the room which's mate you want
     * @return the Mate from the certain room
     */
    public Mate findMateByRoom(int room) {
        if (room > size) new IndexOutOfBoundsException("Room number is too big for the size of the House");
        if (room <= 0) new IndexOutOfBoundsException("There is no room number 0 or lower");
        return mates.get(room - 1);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        House house = (House) obj;

        return new EqualsBuilder().append(id, house.id)
                .append(mates, house.mates)
                .append(size, house.size)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(mates)
                .append(size)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("Id", id)
                .append("Mates", mates)
                .append("Size", size)
                .toString();
    }
}

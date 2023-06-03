package commons;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany(mappedBy = "house", orphanRemoval = true)
    private final List<Mate> mates;
    private int size;

    public House() {
        size = 10;
        mates = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            mates.add(null);
        }
    }

    public House(int houseSize) {
        if (houseSize <= 0)
            throw new IllegalArgumentException("House size cannot cannot be 0 or smaller");
        size = houseSize;
        mates = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            mates.add(null);
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Mate> getMates() {
        return mates;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        if (size <= 0)
            throw new IllegalArgumentException("House size cannot cannot be 0 or smaller");
        int diff = this.size - size;
        this.size = size;
        if (diff > 0) {
            this.mates.subList(this.size, this.size + diff).clear();
        } else if (diff < 0) {
            for (int i = this.size + diff; i < this.size; i++) {
                this.mates.add(i, null);
            }
        }
    }

    /**
     * Change a Mate of a specific room
     * @param room room number which has a change in it
     * @param mate the new Mate that replaces the old
     */
    public void changeMate(int room, Mate mate) {
        if (room > size) throw new IndexOutOfBoundsException("Room number is too big for the size of the House");
        if (room <= 0) throw new IndexOutOfBoundsException("There is no room number 0 or lower");
        if (mates.get(room - 1) != null)
            mates.get(room - 1).setHouse(null);
        mates.set(room - 1, mate);
        mate.setHouse(this);
    }

    /**
     * Gets a Mate from a certain room number
     * @param room the room which's mate you want
     * @return the Mate from the certain room
     */
    public Mate findMateByRoom(int room) {
        if (room > size) throw new IndexOutOfBoundsException("Room number is too big for the size of the House");
        if (room <= 0) throw new IndexOutOfBoundsException("There is no room number 0 or lower");
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

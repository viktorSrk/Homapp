package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class HouseTest {

    private Mate mate;
    private Mate mate2;

    @BeforeEach
    public void setup() {
        mate = Mockito.mock(Mate.class);
        mate2 = Mockito.mock(Mate.class);
    }

    @Test
    public void emptyConstructor() {
        House h = new House();
        assertEquals(10, h.getSize());
        assertEquals(10, h.getMates().size());
    }

    @Test
    public void constructor() {
        House h = new House(1);
        assertEquals(1, h.getSize());
        assertEquals(1, h.getMates().size());
    }

    @Test
    public void constructorZeroCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new House(0));
    }

    @Test
    public void gettersSettersId() {
        House h = new House();
        h.setId(1862L);
        assertEquals(1862L, h.getId());
    }

    @Test
    public void gettersSettersSize() {
        House h = new House();
        assertThrows(IllegalArgumentException.class, () -> h.setSize(0));
        h.setSize(1);
        assertEquals(1, h.getSize());
        assertEquals(1, h.getMates().size());
        h.setSize(5);
        assertEquals(5, h.getSize());
        assertEquals(5, h.getMates().size());
    }

    @Test
    public void changeMateIllegalRoom() {
        House h = new House(5);
        assertThrows(IndexOutOfBoundsException.class, () -> h.changeMate(0, mate));
        assertThrows(IndexOutOfBoundsException.class, () -> h.changeMate(6, mate));
    }

    @Test
    public void changeMate() {
        House h = new House(5);
        h.changeMate(1, mate);
        assertEquals(mate, h.getMates().get(0));
        assertEquals(5, h.getMates().size());
        Mockito.verify(mate).setHouse(h);
        h.changeMate(5, mate2);
        assertEquals(mate2, h.getMates().get(4));
        assertEquals(5, h.getMates().size());
        Mockito.verify(mate2).setHouse(h);
    }

    @Test
    public void changeMate2() {
        House h = new House(5);
        h.changeMate(1, mate);
        assertEquals(mate, h.getMates().get(0));
        assertEquals(5, h.getMates().size());
        Mockito.verify(mate).setHouse(h);
        h.changeMate(1, mate2);
        assertEquals(mate2, h.getMates().get(0));
        assertEquals(5, h.getMates().size());
        Mockito.verify(mate).setHouse(null);
        Mockito.verify(mate2).setHouse(h);
    }

    @Test
    public void findMateByRoomIllegalRoom() {
        House h = new House(5);
        assertThrows(IndexOutOfBoundsException.class, () -> h.findMateByRoom(0));
        assertThrows(IndexOutOfBoundsException.class, () -> h.findMateByRoom(6));
    }

    @Test
    public void findMateByRoom() {
        House h = new House(5);
        h.changeMate(1, mate);
        h.changeMate(5, mate2);
        assertEquals(mate, h.findMateByRoom(1));
        assertNull(h.findMateByRoom(2));
        assertNull(h.findMateByRoom(3));
        assertNull(h.findMateByRoom(4));
        assertEquals(mate2, h.findMateByRoom(5));
    }
}
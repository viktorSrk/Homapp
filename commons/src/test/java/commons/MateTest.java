package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class MateTest {

    private House house;
    private Mate m;

    @BeforeEach
    public void setup() {
        m = new Mate();
        house = Mockito.mock(House.class);
    }

    @Test
    public void testGetter() {
        Mate m1 = new Mate("Viktor", "Sersik", true);
        assertEquals("Viktor", m1.getName());
        assertEquals("Sersik", m1.getLastname());
        assertTrue(m1.isHj());
    }

    @Test
    public void testSetter() {
        m.setName("Viktor");
        m.setLastname("Sersik");
        m.setHj(true);
        m.setId(1862L);
        m.setHouse(house);

        assertEquals("Viktor", m.getName());
        assertEquals("Sersik", m.getLastname());
        assertTrue(m.isHj());
        assertEquals(1862L, m.getId());
        assertEquals(house, m.getHouse());
    }
}
package server.api;

import commons.House;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class HouseControllerTest {
    private TestHouseRepository repo;
    private HouseController sut;

    @BeforeEach
    public void setup() {
        repo = new TestHouseRepository();
        sut = new HouseController(repo);
    }

    @Test
    public void getAll() {
        List<House> actual = sut.getAll();
        assertTrue(repo.calledMethods.contains("findAll"));
        assertEquals(new ArrayList<>(), actual);
    }

    @Test
    public void getByNegativeId() {
        ResponseEntity<House> actual = sut.getById(-1L);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertFalse(repo.calledMethods.contains("findById"));
    }

    @Test
    public void getByNexistingId() {
        ResponseEntity<House> actual = sut.getById(2L);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertFalse(repo.calledMethods.contains("findById"));
    }

    @Test
    public void getById() {
        House h1 = new House();
        House h2 = new House(18);

        sut.add(h1);
        sut.add(h2);

        assertEquals(h1, sut.getById(h1.getId()).getBody());
        assertEquals(h2, sut.getById(h2.getId()).getBody());
        assertTrue(repo.calledMethods.contains("findById"));
    }

    @Test
    public void addNull() {
        ResponseEntity<House> actual = sut.add(null);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertFalse(repo.calledMethods.contains("save"));
    }

    @Test
    public void add() {
        House h = new House();
        assertFalse(repo.houses.contains(h));

        ResponseEntity<House> actual = sut.add(h);
        assertTrue(repo.houses.contains(h));
        assertEquals(h, actual.getBody());
        assertTrue(repo.calledMethods.contains("save"));
    }

    @Test
    public void deleteNull() {
        ResponseEntity<House> actual = sut.delete(null);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertFalse(repo.calledMethods.contains("delete"));
    }

    @Test
    public void deleteNexisting() {
        ResponseEntity<House> actual = sut.delete(new House());
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertFalse(repo.calledMethods.contains("delete"));
    }

    @Test
    public void delete() {
        House h = new House();
        sut.add(h);
        assertTrue(repo.houses.contains(h));

        ResponseEntity<House> actual = sut.delete(h);
        assertTrue(repo.calledMethods.contains("delete"));
        assertFalse(repo.houses.contains(h));
        assertEquals(h, actual.getBody());
    }
}

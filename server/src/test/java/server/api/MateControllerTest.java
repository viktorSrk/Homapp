package server.api;

import commons.House;
import commons.Mate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class MateControllerTest {
    private TestMateRepository repo;
    private House testHouse;
    private MateController sut;

    @BeforeEach
    public void setup() {
        TestHouseRepository houseRepo = new TestHouseRepository();
        HouseController houseSut = new HouseController(houseRepo);

        testHouse = houseSut.add(new House()).getBody();
        assertNotNull(testHouse);

        repo = new TestMateRepository();
        sut = new MateController(repo, houseRepo);
    }

    @Test
    public void getAll() {
        List<Mate> actual = sut.getAll();
        assertTrue(repo.calledMethods.contains("findAll"));
        assertEquals(new ArrayList<>(), actual);
    }

    @Test
    public void getByNegativeId() {
        ResponseEntity<Mate> actual = sut.getById(-1L);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertFalse(repo.calledMethods.contains("findById"));
    }

    @Test
    public void getByNexistingId() {
        ResponseEntity<Mate> actual = sut.getById(1L);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertFalse(repo.calledMethods.contains("findById"));
    }

    @Test
    public void getById() {
        Mate m1 = new Mate();
        Mate m2 = new Mate();
        sut.add(m1, testHouse.getId());
        sut.add(m2, testHouse.getId());
        assertEquals(m1, sut.getById(m1.getId()).getBody());
        assertEquals(m2, sut.getById(m2.getId()).getBody());
        assertTrue(repo.calledMethods.contains("findById"));
    }

    @Test
    public void addNull() {
        ResponseEntity<Mate> actual = sut.add(null, testHouse.getId());
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertFalse(repo.calledMethods.contains("save"));
    }

    @Test
    public void add() {
        Mate m = new Mate();
        assertFalse(repo.mates.contains(m));

        ResponseEntity<Mate> actual = sut.add(m, testHouse.getId());
        assertTrue(repo.mates.contains(m));
        assertEquals(m, actual.getBody());
        assertTrue(repo.calledMethods.contains("save"));
    }

    @Test
    public void deleteNull() {
        ResponseEntity<Mate> actual = sut.delete(null);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertFalse(repo.calledMethods.contains("delete"));
    }

    @Test
    public void deleteNexisting() {
        ResponseEntity<Mate> actual = sut.delete(new Mate());
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertFalse(repo.calledMethods.contains("delete"));
    }

    @Test
    public void delete() {
        Mate m = new Mate();
        sut.add(m, testHouse.getId());
        assertTrue(repo.mates.contains(m));

        ResponseEntity<Mate> actual = sut.delete(m);
        assertTrue(repo.calledMethods.contains("delete"));
        assertFalse(repo.mates.contains(m));
        assertEquals(m, actual.getBody());
    }

    /*
    @Test
    public void replaceWithNull() {
        Mate m = new Mate();
        sut.add(m, testHouse.getId());

        ResponseEntity<Mate> actual = sut.replace(null, m.getId());
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void replaceNexisting() {
        ResponseEntity<Mate> actual = sut.replace(new Mate(), 0L);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
        assertFalse(repo.calledMethods.contains("save"));
    }

    @Test
    public void replace() {
        Mate m = new Mate();
        Mate m2 = new Mate();
        sut.add(m, testHouse.getId());
        assertTrue(repo.mates.contains(m));
        assertFalse(repo.mates.contains(m2));

        ResponseEntity<Mate> old = sut.replace(m2, m.getId());
        ResponseEntity<Mate>
        assertNotNull();
        assertEquals(m, old.getBody());

        assertEquals(1, repo.mates.size());
        assertTrue(repo.calledMethods.contains("save"));
    }*/
}

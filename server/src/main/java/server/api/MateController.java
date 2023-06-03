package server.api;

import commons.House;
import commons.Mate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import server.database.HouseRepository;
import server.database.MateRepository;

import java.util.List;

@RestController
@RequestMapping("/api/mates")
public class MateController {
    private final MateRepository repo;
    @Autowired
    HouseRepository houseRepo;

    public MateController(MateRepository repo, HouseRepository houseRepo) {
        this.repo = repo;
        this.houseRepo = houseRepo;
    }

    @GetMapping(path = {"", "/"})
    public List<Mate> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mate> getById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.findById(id).get());
    }

    @MessageMapping({"/mates/add/{houseId}"})
    @SendTo("/topic/mates/update")
    public Mate addMessage(Mate mate, @DestinationVariable long houseId) {
        return add(mate, houseId).getBody();
    }

    @PostMapping({"add/{houseId}"})
    public ResponseEntity<Mate> add(
            @RequestBody Mate mate,
            @PathVariable long houseId
    ) {
        if (mate == null)
            return ResponseEntity.badRequest().build();

        House assoc = houseRepo.getById(houseId);
        Mate saved = repo.save(mate);
        saved.setHouse(assoc);
        saved = repo.save(saved);
        return ResponseEntity.ok(saved);
    }

    @MessageMapping({"/mates/delete"})
    @SendTo("/topic/houses/delete")
    public Mate deleteMessage(Mate mate) {
        delete(mate);
        return mate;
    }

    @DeleteMapping(path = {"", "/"})
    public ResponseEntity<Mate> delete(@RequestBody Mate mate) {
        if (mate == null
                || !repo.existsById(mate.getId()))
            return ResponseEntity.badRequest().build();

        repo.delete(mate);
        return ResponseEntity.ok(mate);
    }

    /*
    @MessageMapping("/mate/replace")
    @SendTo("/topic/list/replace")
    public Mate replaceMessage(Mate mate) {
        return replace(mate, mate.getId()).getBody();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mate> replace(
            @RequestBody Mate mate,
            @PathVariable("id") long id
    ) {
        if (mate == null
                || !repo.existsById(id))
            return ResponseEntity.badRequest().build();

        Mate mateToChange = repo.findById(id).isPresent() ? repo.findById(id).get() : null;

        if (mateToChange != null) {
            delete(mate);
            add(mate, mateToChange.getHouse().getId());
            mateToChange.setHouse(null);
        }

        return ResponseEntity.ok(mateToChange);
    }*/
}

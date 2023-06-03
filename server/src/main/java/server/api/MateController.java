package server.api;

import commons.Mate;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import server.database.MateRepository;

import java.util.List;

@RestController
@RequestMapping("/api/mates")
public class MateController {
    private final MateRepository repo;

    public MateController(MateRepository repo) {
        this.repo = repo;
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

    @MessageMapping({"/mates/add"})
    @SendTo("/topic/mates/update")
    public Mate addMessage(Mate mate) {
        return add(mate).getBody();
    }

    @PostMapping(path = {"", "/"})
    public ResponseEntity<Mate> add(@RequestBody Mate mate) {
        Mate saved = repo.save(mate);
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
        repo.delete(mate);
        return ResponseEntity.ok(mate);
    }
}

package server.api;

import commons.House;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import server.database.HouseRepository;

import java.util.List;

@RestController
@RequestMapping("/api/houses")
public class HouseController {
    private final HouseRepository repo;

    public HouseController(HouseRepository repo) {
        this.repo = repo;
    }

    @GetMapping(path = { "", "/" })
    public List<House> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<House> getById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.findById(id).get());
    }

    @MessageMapping({"/houses/add"})
    @SendTo("/topic/houses/update")
    public House addMessage(House house) {
        return add(house).getBody();
    }
    @PostMapping(path = { "", "/" })
    public ResponseEntity<House> add(@RequestBody House house) {
        House saved = repo.save(house);
        return ResponseEntity.ok(saved);
    }

    @MessageMapping("/houses/delete")
    @SendTo("/topic/houses/delete")
    public House deleteMessage(House house) {
        delete(house);
        return house;
    }

    @DeleteMapping(path = { "", "/" })
    public ResponseEntity<House> delete(@RequestBody House house) {
        repo.delete(house);
        return ResponseEntity.ok(house);
    }
}

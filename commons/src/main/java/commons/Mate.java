package commons;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Mate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public Mate() {

    }
}

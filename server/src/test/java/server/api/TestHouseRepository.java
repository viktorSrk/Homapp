package server.api;

import commons.House;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.HouseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TestHouseRepository implements HouseRepository {
    public final List<House> houses = new ArrayList<>();
    public final List<String> calledMethods = new ArrayList<>();
    private void call(String name) {
        calledMethods.add(name);
    }
    @Override
    public List<House> findAll() {
        calledMethods.add("findAll");
        return houses;
    }

    @Override
    public long count() {
        return houses.size();
    }

    @Override
    public <S extends House> S save(S entity) {
        call("save");
        entity.setId((long) houses.size());
        houses.add(entity);
        return entity;
    }

    @Override
    public Optional<House> findById(Long aLong) {
        call("findById");
        House res = null;
        for (House h : houses) {
            if (h.getId() == aLong) {
                res = h;
                break;
            }
        }
        return Optional.ofNullable(res);
    }

    @Override
    public boolean existsById(Long aLong) {
        call("existsById");
        return find(aLong).isPresent();
    }

    @Override
    public House getById(Long aLong) {
        call("getById");
        return find(aLong).get();
    }

    private Optional<House> find(Long id) {
        return houses.stream().filter(q -> q.getId() == id).findFirst();
    }

    @Override
    public void delete(House entity) {
        call("delete");
        houses.remove(entity);
    }

    @Override
    public List<House> findAll(Sort sort) {
        return null;
    }
    @Override
    public Page<House> findAll(Pageable pageable) {
        return null;
    }
    @Override
    public List<House> findAllById(Iterable<Long> longs) {
        return null;
    }
    @Override
    public void deleteById(Long aLong) {

    }
    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }
    @Override
    public void deleteAll(Iterable<? extends House> entities) {

    }
    @Override
    public void deleteAll() {

    }
    @Override
    public <S extends House> List<S> saveAll(Iterable<S> entities) {
        return null;
    }
    @Override
    public void flush() {

    }
    @Override
    public <S extends House> S saveAndFlush(S entity) {
        return null;
    }
    @Override
    public <S extends House> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }
    @Override
    public void deleteAllInBatch(Iterable<House> entities) {

    }
    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }
    @Override
    public void deleteAllInBatch() {

    }
    @Override
    public House getOne(Long aLong) {
        return null;
    }
    @Override
    public <S extends House> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }
    @Override
    public <S extends House> List<S> findAll(Example<S> example) {
        return null;
    }
    @Override
    public <S extends House> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }
    @Override
    public <S extends House> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }
    @Override
    public <S extends House> long count(Example<S> example) {
        return 0;
    }
    @Override
    public <S extends House> boolean exists(Example<S> example) {
        return false;
    }
    @Override
    public <S extends House, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}

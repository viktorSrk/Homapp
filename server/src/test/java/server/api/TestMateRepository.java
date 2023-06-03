package server.api;

import commons.Mate;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.database.MateRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public class TestMateRepository implements MateRepository {
    public final List<Mate> mates = new ArrayList<>();
    public final List<String> calledMethods = new ArrayList<>();

    private void call(String name) {
        calledMethods.add(name);
    }

    private Optional<Mate> find(Long id) {
        return mates.stream().filter(q -> q.getId() == id).findFirst();
    }

    @Override
    public List<Mate> findAll() {
        call("findAll");
        return mates;
    }

    @Override
    public void delete(Mate entity) {
        call("delete");
        mates.remove(entity);
    }

    @Override
    public <S extends Mate> S save(S entity) {
        call("save");
        entity.setId((long) mates.size());
        if (!mates.contains(entity)) mates.add(entity);
        return entity;
    }

    @Override
    public Optional<Mate> findById(Long aLong) {
        call("findById");
        Mate res = null;
        for (Mate m : mates) {
            if (Objects.equals(m.getId(), aLong)) {
                res = m;
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
    public Mate getById(Long aLong) {
        call("getById");
        return find(aLong).get();
    }

    @Override
    public <S extends Mate> List<S> findAll(Example<S> example, Sort sort) {
        call("findAll");
        return (List<S>) mates;
    }


    @Override
    public List<Mate> findAll(Sort sort) {
        return null;
    }
    @Override
    public Page<Mate> findAll(Pageable pageable) {
        return null;
    }
    @Override
    public List<Mate> findAllById(Iterable<Long> longs) {
        return null;
    }
    @Override
    public long count() {
        return 0;
    }
    @Override
    public void deleteById(Long aLong) {

    }
    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }
    @Override
    public void deleteAll(Iterable<? extends Mate> entities) {

    }
    @Override
    public void deleteAll() {

    }
    @Override
    public <S extends Mate> List<S> saveAll(Iterable<S> entities) {
        return null;
    }
    @Override
    public void flush() {

    }
    @Override
    public <S extends Mate> S saveAndFlush(S entity) {
        return null;
    }
    @Override
    public <S extends Mate> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }
    @Override
    public void deleteAllInBatch(Iterable<Mate> entities) {

    }
    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }
    @Override
    public void deleteAllInBatch() {

    }
    @Override
    public Mate getOne(Long aLong) {
        return null;
    }
    @Override
    public <S extends Mate> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }
    @Override
    public <S extends Mate> List<S> findAll(Example<S> example) {
        return null;
    }
    @Override
    public <S extends Mate> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }
    @Override
    public <S extends Mate> long count(Example<S> example) {
        return 0;
    }
    @Override
    public <S extends Mate> boolean exists(Example<S> example) {
        return false;
    }
    @Override
    public <S extends Mate, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}

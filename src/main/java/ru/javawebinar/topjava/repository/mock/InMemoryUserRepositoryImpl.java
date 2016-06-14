package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.06.2015.
 */
@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger count = new AtomicInteger(0);
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    @Override
    public boolean delete(int id) {
       try{
            repository.remove(id);
            LOG.info("delete " + id);
            return true;
        } catch (Exception e){
            LOG.info("Can't delete " + id);
            return false;
        }
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);
        if (user.isNew()) {
            user.setId(count.incrementAndGet());
        }
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        if (repository.containsKey(id)) {
            LOG.info("get " + id);
            return repository.get(id);
        } else {
            LOG.info("Can't get " + id);
            return null;
        }
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        return repository.values().stream().sorted((u1, u2) -> u1.getName().compareTo(u2.getName())).collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        User user;
        try{
            user = repository.values().stream().filter(u -> u.getEmail().equals(email)).findFirst().get();
            LOG.info("getByEmail " + email);
            return user;
        }catch (NoSuchElementException e){
            LOG.info("Can't getByEmail " + email);
            return null;
        }

    }
}

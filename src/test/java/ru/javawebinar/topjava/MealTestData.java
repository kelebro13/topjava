package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    private static final Comparator<UserMeal> USER_MEAL_COMPARATOR = Comparator.comparing(UserMeal::getDateTime).reversed();

    private static Map<Integer, Map<Integer, UserMeal>> repository = new ConcurrentHashMap<>();
    private static AtomicInteger counter = new AtomicInteger(100001);


    public static void resetRepository() {
        repository = new ConcurrentHashMap<>();

        List<UserMeal> USER_MEAL_LIST = Arrays.asList(
                new UserMeal(counter.incrementAndGet(), LocalDateTime.of(2016, 05, 30, 10, 00), "Завтрак", 500),
                new UserMeal(counter.incrementAndGet(), LocalDateTime.of(2016, 05, 30, 13, 00), "Обед", 1000),
                new UserMeal(counter.incrementAndGet(), LocalDateTime.of(2016, 05, 30, 20, 00), "Ужин", 500),
                new UserMeal(counter.incrementAndGet(), LocalDateTime.of(2016, 05, 31, 10, 00), "Завтрак", 1000),
                new UserMeal(counter.incrementAndGet(), LocalDateTime.of(2016, 05, 31, 13, 00), "Обед", 500),
                new UserMeal(counter.incrementAndGet(), LocalDateTime.of(2016, 05, 31, 20, 00), "Ужин", 510));

        List<UserMeal> ADMIN_MEAL_LIST = Arrays.asList(

                new UserMeal(counter.incrementAndGet(), LocalDateTime.of(2016, 06, 01, 14, 00), "Админ ланч", 510),
                new UserMeal(counter.incrementAndGet(), LocalDateTime.of(2016, 06, 01, 21, 00), "Админ ужин", 1500));

        repository.put(USER_ID, USER_MEAL_LIST.stream().collect(Collectors.toMap(UserMeal::getId, Function.identity())));
        repository.put(ADMIN_ID, ADMIN_MEAL_LIST.stream().collect(Collectors.toMap(UserMeal::getId, Function.identity())));
        counter.set(100001);
    }

    public static List<UserMeal> getUserMealList(int user_id) {
        return repository.get(user_id).values().stream().sorted(USER_MEAL_COMPARATOR).collect(Collectors.toList());
    }

    public static UserMeal get(int id, int user_id) {
        return repository.get(user_id).get(id);
    }

    public static void delete(int id, int user_id) {
        repository.get(user_id).remove(id);
    }

    public static void save(UserMeal userMeal, int user_id) {
        repository.get(user_id).put(userMeal.getId(), userMeal);
    }

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(UserMeal::toString);


}

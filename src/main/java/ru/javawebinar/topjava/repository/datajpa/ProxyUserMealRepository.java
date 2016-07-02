package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface ProxyUserMealRepository extends JpaRepository<UserMeal, Integer> {


    @Override
    UserMeal save(UserMeal userMeal);

    @Transactional
    @Modifying
    int deleteByIdAndUserId(int id, int userId);

    UserMeal getByIdAndUserId(int id, int userId);

    List<UserMeal> findAllByUserIdOrderByDateTimeDesc(int userId);

    List<UserMeal> findAllByDateTimeBetweenAndUserIdOrderByDateTimeDesc(LocalDateTime startDate, LocalDateTime endDate, int userId);


}

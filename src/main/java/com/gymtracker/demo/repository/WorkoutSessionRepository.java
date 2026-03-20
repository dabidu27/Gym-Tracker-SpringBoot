package com.gymtracker.demo.repository;

import com.gymtracker.demo.entity.User;
import com.gymtracker.demo.entity.WorkoutSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WorkoutSessionRepository extends JpaRepository<WorkoutSession, Long> {

    public Optional<WorkoutSession> findByIdAndUser(Long id, User user);

    //method with custom jpql sql query that returns no.of workout sessions per day
    //the method returns a list of arrays of Object, because each row returned from the db corresponds to an array of object
    //in jpql queries, we use the entity lass name as table name
    @Query("SELECT CAST(ws.startedAt as date) as date, count(ws) as count " +
    "from WorkoutSession ws " +
    "where ws.user = :user " +
    "and ws.startedAt >= :oneYearAgo " +
    "group by cast(ws.startedAt as date) " +
    "order by cast(ws.startedAt as date)")
    //:user, :oneYearAgo = parameters
    public List<Object[]> findActivityByUser(@Param("user") User user, @Param("oneYearAgo")LocalDateTime oneYearAgo); //@Param("user") User user - the User object passed to this function corresponds to the user parameter in the query
}

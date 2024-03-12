package com.akul.taskslist.repository;

import com.akul.taskslist.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findUserByUsername(String username);

    @Query(value = """
            SELECT exists(
            SELECT   1
            FROM taskslist.users_tasks ut
            WHERE ut.user_id = :userId
            AND ut.task_id =:taskId
            )
            """, nativeQuery = true)
    boolean isTaskOwner(@Param("userId") Long userId, @Param("taskId") Long taskId);

}

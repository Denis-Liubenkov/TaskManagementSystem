package com.tms.taskmanagementsystem.repository;

import com.tms.taskmanagementsystem.domain.Priority;
import com.tms.taskmanagementsystem.domain.Status;
import com.tms.taskmanagementsystem.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    @NonNull
    Optional<Task> findById(@NonNull Integer id);

    @NonNull
    Page<Task> findAll(@NonNull Pageable pageable);

    Page<Task> findByTitle(String title, Pageable pageable);

    @Query("select p from task p where p.priority='HIGH'")
    List<Task> findByPriority(Priority priority);

    @Query("select s from task s where s.status='IN_WAITING'")
    List<Task> findByStatus(Status status);

    void deleteById(@NonNull Integer id);
}


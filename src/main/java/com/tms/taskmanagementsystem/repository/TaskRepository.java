package com.tms.taskmanagementsystem.repository;

import com.tms.taskmanagementsystem.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    @NonNull
    //Optional<Task> findById(@NonNull Integer id);
    Page<Task>findAllByStatusAndPriority(String status,String priority, Pageable pageable);
@NonNull
    List<Task> findAll();

    void deleteById(@NonNull Integer id);
}


package com.tms.taskmanagementsystem.repository;

import com.tms.taskmanagementsystem.domain.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Integer> {

    void deleteById(@NonNull Integer id);
}

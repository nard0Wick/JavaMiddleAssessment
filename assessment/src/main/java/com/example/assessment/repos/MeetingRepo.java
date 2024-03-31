package com.example.assessment.repos;

import com.example.assessment.models.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeetingRepo  extends JpaRepository<Meeting, Long> {
    boolean existsByTitle(String title);
    Optional<Meeting> findByTitle(String title);
}

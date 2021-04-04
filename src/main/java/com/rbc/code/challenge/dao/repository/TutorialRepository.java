package com.rbc.code.challenge.dao.repository;

import com.rbc.code.challenge.dao.entity.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
}

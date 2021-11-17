package io.github.tn1.server.domain.question.domain.repository;

import io.github.tn1.server.domain.question.domain.QuestionResult;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionResultRepository extends JpaRepository<QuestionResult, Long> {
}

package io.github.tn1.server.domain.question.domain.repository;

import io.github.tn1.server.domain.question.domain.Question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
	@Override
	Page<Question> findAll(Pageable pageable);
}

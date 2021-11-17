package io.github.tn1.server.domain.question.result;

import io.github.tn1.server.domain.question.domain.Question;
import io.github.tn1.server.domain.question.domain.QuestionResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QuestionResultTest {

    @Test
    @DisplayName("Builder를 활용하여 Report객체를 생성하는 메소드")
    void createByBuilder() {
        //given
        String reason = "Test";
        Question question = Question.builder()
                .description("test")
                .build();
        //when
        QuestionResult questionResult = QuestionResult.builder()
                .reason(reason)
                .question(question)
                .build();
        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(reason, questionResult.getReason()),
                () -> Assertions.assertEquals(question, questionResult.getQuestion())
        );
    }

}
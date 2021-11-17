package io.github.tn1.server.domain.question;

import io.github.tn1.server.domain.question.domain.Question;
import io.github.tn1.server.domain.user.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class QuestionTest {

    @Test
    @DisplayName("Builder를 활용하여 Question객체를 생성하는 메소드")
    void createByBuilder() {
        //given
        String title = "제목";
        String description = "설명";
        User user = User.builder()
                .name("문의자")
                .build();
        //when
        Question question = Question.builder()
                .title(title)
                .description(description)
                .user(user)
                .build();
        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(title, question.getTitle()),
                () -> Assertions.assertEquals(description, question.getDescription()),
                () -> Assertions.assertEquals(user, question.getUser())
        );
    }

}
package io.github.tn1.server.entity.report.feed_report;

import io.github.tn1.server.entity.feed.Feed;
import io.github.tn1.server.entity.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FeedReportTest {

    @Test
    @DisplayName("Builder를 활용하여 FeedReport객체를 생성하는 메소드")
    void createByBuilder() {
        //given
        String title = "제목";
        String description = "설명";
        User user = User.builder()
                .name("구매자")
                .build();

        //when
        Feed feed = Feed.builder()
                .title(title)
                .description(description)
                .isUsedItem(true)
                .price(10000)
                .user(user)
                .build();

        //given
        Assertions.assertAll(
                () -> Assertions.assertEquals(title, feed.getTitle()),
                () -> Assertions.assertEquals(description, feed.getDescription()),
                () -> Assertions.assertEquals(user, feed.getUser())
        );
    }

}
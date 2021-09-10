package io.github.tn1.server.entity.feed.medium;

import io.github.tn1.server.entity.feed.Feed;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FeedMediumTest {

    @Test
    @DisplayName("Builder를 활용하여 FeedMedium객체를 생성하는 메소드")
    void createByBuilder() {
        //given
        Feed feed = Feed.builder()
                .title("제목")
                .build();
        String fileName = "name";
        //then
        FeedMedium medium = FeedMedium.builder()
                .feed(feed)
                .fileName(fileName)
                .build();
        //when
        Assertions.assertAll(
                () -> Assertions.assertEquals(feed, medium.getFeed()),
                () -> Assertions.assertEquals(fileName, medium.getFileName())
        );
    }

}
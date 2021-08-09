package io.github.tn1.server.entity.feed.medium;

import io.github.tn1.server.entity.feed.Feed;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MediumTest {

    @Test
    @DisplayName("Builder를 활용하여 Medium객체를 생성하는 메소드")
    void createByBuilder() {
        //given
        Feed feed = Feed.builder()
                .title("제목")
                .build();
        String path = "경로";
        //then
        Medium medium = Medium.builder()
                .feed(feed)
                .path(path)
                .build();
        //when
        Assertions.assertAll(
                () -> Assertions.assertEquals(feed, medium.getFeed()),
                () -> Assertions.assertEquals(path, medium.getPath())
        );
    }

}
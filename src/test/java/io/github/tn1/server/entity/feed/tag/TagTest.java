package io.github.tn1.server.entity.feed.tag;

import io.github.tn1.server.entity.feed.Feed;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagTest {

    @Test
    @DisplayName("Builder를 활용하여 Tag객체를 생성하는 메소드")
    void createByBuilder() {
        //given
        Feed feed = Feed.builder()
                .title("제목")
                .build();
        String value = "tag";
        //when
        Tag tag = Tag.builder()
                .feed(feed)
                .tag(value)
                .build();
        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(feed, tag.getFeed()),
                () -> Assertions.assertEquals(value, tag.getTag())
        );
    }

}
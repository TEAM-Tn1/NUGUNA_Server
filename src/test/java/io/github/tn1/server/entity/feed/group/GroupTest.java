package io.github.tn1.server.entity.feed.group;

import io.github.tn1.server.entity.feed.Feed;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class GroupTest {

    @Test
    @DisplayName("Builder를 활용하여 Group객체를 생성하는 메소드")
    void createByBuilder() {
        //given
        LocalDate date = LocalDate.of(2021,8,10);
        Feed feed = Feed.builder()
                .title("제목")
                .isUsedItem(false)
                .build();
        //when
        Group group = Group.builder()
                .feed(feed)
                .recruitmentDate(date)
                .headCount(10)
                .build();
        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(date, group.getRecruitmentDate()),
                () -> Assertions.assertEquals(feed, group.getFeed())
        );
    }

}
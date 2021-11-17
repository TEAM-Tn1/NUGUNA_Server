package io.github.tn1.server.domain.like;

import io.github.tn1.server.domain.feed.domain.Feed;
import io.github.tn1.server.domain.like.domain.Like;
import io.github.tn1.server.domain.user.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LikeTest {

    @Test
    @DisplayName("Builder를 활용하여 Like객체를 생성하는 메소드")
    void createByBuilder() {
        //given
        Feed feed = Feed.builder()
                .title("피드")
                .build();
        User user = User.builder()
                .name("구매자")
                .build();
        //when
        Like like = Like.builder()
                .feed(feed)
                .user(user)
                .build();
        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(feed, like.getFeed()),
                () -> Assertions.assertEquals(user, like.getUser())
        );
    }

}
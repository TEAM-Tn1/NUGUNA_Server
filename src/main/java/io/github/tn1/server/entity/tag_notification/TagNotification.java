package io.github.tn1.server.entity.tag_notification;

import io.github.tn1.server.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "tbl_tag_notification")
public class TagNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10)
    private String tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email")
    private User user;

    @Builder
    public TagNotification(String tag, User user) {
        this.tag = tag;
        this.user = user;
    }

}

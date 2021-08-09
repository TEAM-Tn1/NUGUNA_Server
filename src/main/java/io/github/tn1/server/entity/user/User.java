package io.github.tn1.server.entity.user;

import io.github.tn1.server.entity.feed.Feed;
import io.github.tn1.server.entity.like.Like;
import io.github.tn1.server.entity.question.Question;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity(name = "tbl_user")
public class User {

    @Id
    @Column(length = 20)
    private String email;

    @Column(length = 4)
    private String name;

    @Column(length = 4)
    private String gcn;

    @Column(length = 3)
    private String roomNumber;

    @Column(length = 20)
    private String accountNumber;

    @Builder
    public User(String email, String name,
                String gcn, String roomNumber, String accountNumber) {
        this.email = email;
        this.name = name;
        this.gcn = gcn;
        this.roomNumber = roomNumber;
        this.accountNumber = accountNumber;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Question> questions = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Like> likes = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Feed> feeds = new HashSet<>();

}

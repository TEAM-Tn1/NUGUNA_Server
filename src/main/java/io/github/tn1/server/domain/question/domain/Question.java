package io.github.tn1.server.domain.question.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import io.github.tn1.server.global.entity.BaseTimeEntity;
import io.github.tn1.server.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_question")
public class Question extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String title;

    @Column(length = 1000)
    private String description;

	@Column(columnDefinition = "BIT(1) default false")
	private boolean isCheck;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email")
    private User user;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "question")
	private QuestionResult questionResult;

    @Builder
    public Question(String title, String description,
                    User user) {
        this.title = title;
        this.description = description;
        this.user = user;
    }

    public void check() {
    	this.isCheck = true;
	}

	public boolean isOwner(String email) {
		return user.getEmail().equals(email);
	}

}

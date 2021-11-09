package io.github.tn1.server.entity.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import io.github.tn1.server.entity.BaseTimeEntity;
import io.github.tn1.server.entity.report.feed_report.FeedReport;
import io.github.tn1.server.entity.report.medium.ReportMedium;
import io.github.tn1.server.entity.report.result.Result;
import io.github.tn1.server.entity.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "tbl_report")
public class Report extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1)
    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    @Column(length = 15)
    private String title;

    @Column(length = 1000)
    private String contents;

    @Column(columnDefinition = "BIT(1) default false")
    private boolean isCheck;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_email")
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "defendant_email")
    private User defendant;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "report")
    private Result result;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "report")
	private ReportMedium reportMedium;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "report")
    private FeedReport feedReport;

    @Builder
    public Report(ReportType reportType, String title,
                  String contents, User reporter, User defendant) {
        this.reportType = reportType;
        this.title = title;
        this.contents = contents;
        this.reporter = reporter;
        this.defendant = defendant;
    }

    public boolean isNotFeedReport() {
    	return !reportType.equals(ReportType.F);
	}

	public boolean isNotUserReport() {
    	return !reportType.equals(ReportType.U);
	}

	public void check() {
    	this.isCheck = true;
	}

	public boolean isNotReporter(String email) {
		return !reporter.getEmail().equals(email);
	}

}

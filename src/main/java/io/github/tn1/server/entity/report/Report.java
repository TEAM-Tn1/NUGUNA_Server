package io.github.tn1.server.entity.report;

import io.github.tn1.server.entity.report.feed_report.FeedReport;
import io.github.tn1.server.entity.report.medium.ReportMedium;
import io.github.tn1.server.entity.report.result.Result;
import io.github.tn1.server.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity(name = "tbl_report")
public class Report {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_email")
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "defendant_email")
    private User defendant;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "report")
    private Result result;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "report")
    private Set<ReportMedium> medium = new HashSet<>();

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

}

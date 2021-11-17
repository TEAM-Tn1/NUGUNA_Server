package io.github.tn1.server.domain.report;

import io.github.tn1.server.domain.report.domain.Report;
import io.github.tn1.server.domain.report.domain.types.ReportType;
import io.github.tn1.server.domain.user.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReportTest {

    @Test
    @DisplayName("Builder를 활용하여 Report객체를 생성하는 메소드")
    void createByBuilder() {
        //given
        ReportType reportType = ReportType.F;
        String title = "Test title";
        User reporter = User.builder()
                .name("신고자")
                .build();
        User defendant = User.builder()
                .name("피의자")
                .build();

        //when
        Report report = Report.builder()
                .reportType(reportType)
                .title(title)
                .contents("test")
                .reporter(reporter)
                .defendant(defendant)
                .build();

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(reportType, report.getReportType()),
                () -> Assertions.assertEquals(title, report.getTitle()),
                () -> Assertions.assertEquals(reporter, report.getReporter()),
                () -> Assertions.assertEquals(defendant, report.getDefendant())
        );
    }

}
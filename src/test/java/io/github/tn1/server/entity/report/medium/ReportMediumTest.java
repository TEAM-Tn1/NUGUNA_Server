package io.github.tn1.server.entity.report.medium;

import io.github.tn1.server.entity.report.Report;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReportMediumTest {

    @Test
    @DisplayName("Builder를 활용하여 ReportMedium객체를 생성하는 메소드")
    void createByBuilder() {
        //given
        Report report = Report.builder()
                .title("신고")
                .build();

        String path = "자료위치";

        //when
        ReportMedium medium = ReportMedium.builder()
                .report(report)
                .path(path)
                .build();

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(report, medium.getReport()),
                () -> Assertions.assertEquals(path, medium.getPath())
        );
    }

}
package io.github.tn1.server.domain.report.result;

import io.github.tn1.server.domain.report.domain.Report;
import io.github.tn1.server.domain.report.domain.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ResultTest {

    @Test
    @DisplayName("Builder를 활용하여 Result객체를 생성하는 메소드")
    void createByBuilder() {
        //given
        String reason = "원인";
        Report report = Report.builder()
                .title("신고")
                .build();

        //when
        Result result = Result.builder()
                .reason(reason)
                .report(report)
                .build();

        //then
        Assertions.assertAll(
                () -> Assertions.assertEquals(reason, result.getReason()),
                () -> Assertions.assertEquals(report, result.getReport())
        );
    }

}
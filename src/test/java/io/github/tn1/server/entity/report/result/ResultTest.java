package io.github.tn1.server.entity.report.result;

import io.github.tn1.server.entity.report.Report;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
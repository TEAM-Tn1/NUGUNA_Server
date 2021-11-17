package io.github.tn1.server.global.utils.api;

import feign.Logger;
import feign.codec.ErrorDecoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableFeignClients(basePackages = "io.github.tn1.server.global.utils.api")
@Import(FeignClientErrorDecoder.class)
public class FeignConfiguration {

    /**
     * Set the Feign specific log level to log client REST requests.
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }

    /**
     * Configure default feign client error decoder.
     */
    @Bean
    @ConditionalOnMissingBean(value = ErrorDecoder.class)
    public FeignClientErrorDecoder commonFeignErrorDecoder() {
        return new FeignClientErrorDecoder();
    }

}

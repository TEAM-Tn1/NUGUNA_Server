package io.github.tn1.server.global.utils.api;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import io.github.tn1.server.global.utils.api.exception.OtherServerBadRequestException;
import io.github.tn1.server.global.utils.api.exception.OtherServerExpiredTokenException;
import io.github.tn1.server.global.utils.api.exception.OtherServerForbiddenException;
import io.github.tn1.server.global.utils.api.exception.OtherServerUnauthorizedException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

    	if(response.status() >= 400) {
			switch (response.status()){
				case 401:
					throw new OtherServerUnauthorizedException();
				case 403:
					throw new OtherServerForbiddenException();
				case 419:
					throw new OtherServerExpiredTokenException();
				default:
					throw new OtherServerBadRequestException();
			}
		}

        return FeignException.errorStatus(methodKey, response);
    }

}

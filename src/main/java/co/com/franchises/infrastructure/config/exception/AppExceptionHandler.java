package co.com.franchises.infrastructure.config.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppExceptionHandler implements ErrorWebExceptionHandler {
    private static final String DEFAULT_MESSAGE = "An error occurred, please try again later";
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ErrorResponse errorResponse;

        try {
            switch (ex) {
                case BusinessException businessEx -> {
                    exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
                    errorResponse = new ErrorResponse(businessEx.getCode(), ex.getMessage(), businessEx.getDetails());
                }
                case NotFoundException notFoundEx -> {
                    exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
                    errorResponse = new ErrorResponse(notFoundEx.getCode(), ex.getMessage(), notFoundEx.getDetails());
                }
                case ApplicationException appEx -> {
                    exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
                    errorResponse = new ErrorResponse(appEx.getCode(), ex.getMessage(), appEx.getDetails());
                }
                default -> {
                    exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                    errorResponse = new ErrorResponse("INTERNAL_ERROR", DEFAULT_MESSAGE);
                    log.error("Unhandled exception", ex);
                }
            }

            byte[] responseBytes = objectMapper.writeValueAsBytes(errorResponse);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(responseBytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));

        } catch (Exception e) {
            log.error("Error handling exception", e);
            throw new ExceptionHandlingFailedException("Failed to process error response", e);
        }
    }
}
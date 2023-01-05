package co.com.yoursoft.faultpatterns.infraestructure.entrypoint;

import co.com.yoursoft.faultpatterns.infraestructure.drivenadapter.consumer.ExternalAPICaller;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class FaultPatternsController {

    @Autowired
    private ExternalAPICaller externalAPICaller;


    @GetMapping("/retry")
    @Retry(name = "retryApi", fallbackMethod = "fallbackAfterRetry")
    public ResponseEntity<String> retryApi() {
        return ResponseEntity.ok(externalAPICaller.callApi());
    }

    @GetMapping("/rate-limiter")
    @RateLimiter(name = "rateLimiterApi")
    public String rateLimitApi() {
        return externalAPICaller.callApi();
    }

    @GetMapping("/circuit-breaker")
    @CircuitBreaker(name = "circuit-breaker", fallbackMethod = "fallbackAfterCircuitBreaker")
    public ResponseEntity<String> circuitBreakerApi() {
        return ResponseEntity.ok(externalAPICaller.callApi());
    }

    @GetMapping("/circuit-breaker-by-time")
    @CircuitBreaker(name = "circuit-breaker-by-time", fallbackMethod = "fallbackAfterCircuitBreaker")
    public ResponseEntity<String> circuitBreakerByTimeApi() {
        return ResponseEntity.ok(externalAPICaller.callApi());
    }


    @GetMapping("/time-limiter")
    @TimeLimiter(name = "timeLimiterApi")
    public CompletableFuture<String> timeLimiterApi() {
        return CompletableFuture.supplyAsync(externalAPICaller::callApiWithDelay);
    }

    @GetMapping("/bulkhead")
    @Bulkhead(name="bulkheadApi")
    public String bulkheadApi() {
        return externalAPICaller.callApi();
    }

    public String fallbackAfterRetry(Exception ex) {
        return "all retries have exhausted";
    }

    public ResponseEntity<String> fallbackAfterCircuitBreaker(Exception ex) {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }
}

package co.com.yoursoft.faultpatterns.infraestructure.drivenadapter.consumer;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class ExternalAPICaller {

    public static final int MILLIS = 5000;
    private final RestTemplate restTemplate;

    @Autowired
    public ExternalAPICaller(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String callApi() {
        log.info("Calling external api ...");
        return restTemplate.getForObject("/api/external", String.class);
    }

    public String callApiWithDelay() {
        log.info("Calling external api with %s delay ...", MILLIS);
        String result = restTemplate.getForObject("/api/external", String.class);
        try {
            Thread.sleep(MILLIS);
        } catch (InterruptedException ignore) {
        }
        return result;
    }
}


package net.chaimae.ebankservice.feign;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import net.chaimae.ebankservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerRestClient {

    @GetMapping("/customers/{id}")
    @CircuitBreaker(
            name = "customerService",
            fallbackMethod = "getDefaultCustomer"
    )
    Customer getCustomerById(@PathVariable("id") String id);

    default Customer getDefaultCustomer(String id, Throwable e) {
        return new Customer(id, "Not AVAILABLE");
    }
}

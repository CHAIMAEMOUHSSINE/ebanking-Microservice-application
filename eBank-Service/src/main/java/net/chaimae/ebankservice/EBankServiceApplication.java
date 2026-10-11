package net.chaimae.ebankservice;

import net.chaimae.ebankservice.entities.BankAccount;
import net.chaimae.ebankservice.services.EbankService;
import org.apache.hc.core5.reactor.Command;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class EBankServiceApplication {

	private final EbankService ebankService;

	public EBankServiceApplication(EbankService ebankService) {
		this.ebankService = ebankService;
	}

	public static void main(String[] args) {
		SpringApplication.run(EBankServiceApplication.class, args);
	}
	@Bean

	CommandLineRunner commandLineRunner() {
		return args -> {
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					ebankService.save(BankAccount.builder()
							.balance(Math.random() * 10000)
							.type(Math.random() > 0.5 ? "SAVING-ACCOUNT" : "CURRENT-ACCOUNT")
							.customerId(String.valueOf(i))
							.build());

				}

			}
		};
	}

}

package skotnapplications.superchatbackendchallenge

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate


@SpringBootApplication
class SuperchatBackendChallengeApplication

fun main(args: Array<String>) {
	runApplication<SuperchatBackendChallengeApplication>(*args)
}

@Configuration
class Beans {
	@Bean
	fun restTemplate(): RestTemplate {
		return RestTemplate()
	}
}
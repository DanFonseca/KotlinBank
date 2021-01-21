package daniel.kotlinbank

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinbankApplication

fun main(args: Array<String>) {
	runApplication<KotlinbankApplication>(*args)
}

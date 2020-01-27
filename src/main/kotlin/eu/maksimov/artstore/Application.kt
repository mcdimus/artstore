package eu.maksimov.artstore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.time.Clock

@SpringBootApplication
class Application {

  @Bean
  fun clock() = Clock.systemDefaultZone()

}

fun main(args: Array<String>) {
  runApplication<Application>(*args)
}

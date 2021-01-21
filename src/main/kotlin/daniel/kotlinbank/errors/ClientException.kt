package daniel.kotlinbank.errors

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.BAD_REQUEST)
class ClientException (
        override val message: String
        ) : RuntimeException(message) {
}
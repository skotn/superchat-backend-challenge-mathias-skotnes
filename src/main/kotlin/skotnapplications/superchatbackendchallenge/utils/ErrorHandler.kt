package skotnapplications.superchatbackendchallenge.utils

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ErrorHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(Exception::class)
    fun handleException(): ResponseEntity<Any> {
        return ResponseEntity("Internal error", HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(HttpStatusCodeException::class)
    fun handleException(exception: HttpStatusCodeException): ResponseEntity<Any> {
        return ResponseEntity(exception.localizedMessage, exception.statusCode)
    }

}
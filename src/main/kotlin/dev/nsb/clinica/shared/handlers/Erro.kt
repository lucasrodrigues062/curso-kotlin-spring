package dev.nsb.clinica.shared.handlers

import dev.nsb.clinica.shared.exceptions.ExistsIdException
import dev.nsb.clinica.shared.exceptions.UniqueValueException
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.*

data class Error(
    val field: String,
    val message: String?
) {
}
@RestControllerAdvice
class ErrorHandler(
    val messageSource: MessageSource
) {
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
        fun generalHandler(exception: MethodArgumentNotValidException): List<Error> {
            var errors: MutableList<Error> = mutableListOf()
            var fieldErrors: List<FieldError> = exception.bindingResult.fieldErrors

            for (fieldError in fieldErrors) {
                var message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale())
                var newError = Error( fieldError.field, message)
                errors.add(newError)
            }
            return errors
        }

    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UniqueValueException::class)
    fun uniqueValueHandler(exception: UniqueValueException): Error {

        return Error(exception.field, exception.message)
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ExistsIdException::class)
    fun existsIdHandler(exception: ExistsIdException): Error {

        return Error(exception.field, exception.message)
    }


}
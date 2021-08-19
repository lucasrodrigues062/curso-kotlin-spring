package dev.nsb.clinica.shared.validation

import java.lang.annotation.Documented
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

class PhoneValidator :ConstraintValidator<Phone, String>{
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value != null) {
            return value.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())
        }
        return false
    }

}
/*
* https://medium.com/@demisgomes/implementando-um-custom-validator-no-kotlin-df90313bd17
* https://kotlinlang.org/spec/annotations.html
* */
@Documented
@Constraint(validatedBy = [PhoneValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Phone (
    val message: String = "Please insert a valid phone number with the pattern: +5511999999999",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
) {

}
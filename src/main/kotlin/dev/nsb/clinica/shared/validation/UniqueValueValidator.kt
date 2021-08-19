package dev.nsb.clinica.shared.validation

import dev.nsb.clinica.shared.exceptions.UniqueValueException
import javax.persistence.EntityManager
import javax.persistence.Persistence
import javax.persistence.PersistenceContext
import javax.persistence.Query
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

class UniqueValueValidator : ConstraintValidator<UniqueValue, Any> {

    lateinit var domainAttribute: String
    lateinit var klass: KClass<*>
    @PersistenceContext
    lateinit var manager: EntityManager


    override fun initialize(constraintAnnotation: UniqueValue?) {
        domainAttribute = constraintAnnotation!!.fieldName
        klass = constraintAnnotation.domainClass

    }

    override fun isValid(value: Any, context: ConstraintValidatorContext?): Boolean {

        val query: Query = manager.createQuery("select 1 from ${klass.simpleName} where ${domainAttribute} = :value")
            .setParameter("value",value)

        val list = query.resultList

        if(list.isEmpty()){
            return true
        } else {
            throw UniqueValueException("Already There is a ${domainAttribute} with this value: ${value}", field = domainAttribute)
        }
    }

}
/*
* Documentos usados para criar Anotation customizada em kotlin
* https://medium.com/@demisgomes/implementando-um-custom-validator-no-kotlin-df90313bd17
* https://kotlinlang.org/spec/annotations.html
* https://kotlinlang.org/docs/annotations.html#constructors
* */
@MustBeDocumented
@Constraint(validatedBy = [UniqueValueValidator::class])
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class UniqueValue (
    val fieldName: String,
    val domainClass: KClass<*>,
    val message: String = "Already There is a record with this value",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = [],

) {

}
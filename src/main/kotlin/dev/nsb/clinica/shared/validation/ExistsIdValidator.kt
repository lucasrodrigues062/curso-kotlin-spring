package dev.nsb.clinica.shared.validation


import dev.nsb.clinica.shared.exceptions.ExistsIdException
import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.Query
import kotlin.reflect.KClass


@Service
class ExistsId(

){
    @PersistenceContext
    lateinit var manager: EntityManager

    fun check(    klass: KClass<*>,
                  fieldName: String,
                  value: Any){

        val query: Query = manager.createQuery("select 1 from ${klass.simpleName} where ${fieldName} = :value")
            .setParameter("value",value)

        val list = query.resultList

        if(list.isEmpty()){
            throw ExistsIdException("There is no ${fieldName} with this value: ${value}", field = fieldName)
        }
    }
}

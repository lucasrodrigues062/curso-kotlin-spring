package dev.nsb.clinica.patient

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
class Patient (
    val name: String,
    val email: String,
    val document: String,
    val phone: String,
    val birthDate: LocalDate,
    val externalId: UUID = UUID.randomUUID(),
    val registryDate: LocalDateTime = LocalDateTime.now()

        ){
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

}
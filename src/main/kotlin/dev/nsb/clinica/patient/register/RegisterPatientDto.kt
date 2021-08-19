package dev.nsb.clinica.patient.register

import com.fasterxml.jackson.annotation.JsonFormat
import dev.nsb.clinica.patient.Patient
import dev.nsb.clinica.shared.validation.Phone
import dev.nsb.clinica.shared.validation.UniqueValue
import org.hibernate.validator.constraints.br.CPF
import org.springframework.validation.annotation.Validated
import java.time.LocalDate
import java.util.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Past

data class RegisterPatientDto (
        @field:NotBlank @field:NotNull
        val name: String,
        @field:NotBlank @field:NotNull @field:Email
        val email: String,
        @field:NotBlank @field:NotNull @field:CPF @field:UniqueValue(fieldName = "document", domainClass = Patient::class)
        val document: String,
        @field:NotBlank @field:NotNull @field:Phone
        val phone: String,
        @field:NotNull @field:Past
        @JsonFormat(pattern = "dd/MM/yyyy")
        val birthDate: LocalDate,
        val id: UUID? = null
        ) {


        constructor(patient: Patient) : this(
                name = patient.name,
                email = patient.email,
                document = patient.document,
                phone = patient.document,
                birthDate = patient.birthDate,
                id = patient.externalId
        ) {

        }
        fun toModel(): Patient{
                return Patient(
                        name = this.name,
                        email = this.email,
                        document = this.document,
                        phone = this.phone,
                        birthDate = this.birthDate,

                        )
        }
}
package dev.nsb.clinica.professional.register

import dev.nsb.clinica.professional.Professional
import dev.nsb.clinica.professional.Specialty
import dev.nsb.clinica.shared.validation.CpfCnpjValidator
import dev.nsb.clinica.shared.validation.Phone
import dev.nsb.clinica.shared.validation.UniqueValue
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class RegisterProfessionalDto(
    @field:[NotNull NotBlank]
    val name:String,
    @field:[NotNull NotBlank Email]
    val email: String,
    @field:[NotNull NotBlank Phone]
    val phone: String,
    @field:[NotNull NotBlank CpfCnpjValidator UniqueValue(fieldName = "document", domainClass = Professional::class)]
    val document: String,
    val specialty: List<Specialty>,
    val externalId: String?

) {
    constructor(professional: Professional) : this(
        name = professional.name,
        email = professional.email,
        phone = professional.phone,
        document = professional.document,
        specialty = professional.specialty,
        externalId = professional.externalId.toString()
    ){

    }

    fun toModel(): Professional{
        return Professional(
            name = this.name,
            email = this.email,
            phone = this.phone,
            document = this.document,
            specialty = this.specialty
        )
    }
}
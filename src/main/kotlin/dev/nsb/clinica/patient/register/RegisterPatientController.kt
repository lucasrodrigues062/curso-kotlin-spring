package dev.nsb.clinica.patient.register

import dev.nsb.clinica.patient.PatientRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/paciente")
class RegisterPatientController(
    val repository: PatientRepository
) {
    @PostMapping

    fun register(
        @RequestBody @Valid patientDto: RegisterPatientDto,
        builder: UriComponentsBuilder
    ): ResponseEntity<Any>{

        val patient = repository.save(patientDto.toModel())

        val uri = builder.path("/api/v1/paciente/${patient.externalId}").build().toUri()

        return ResponseEntity.created(uri).body(RegisterPatientDto(patient))

    }
}
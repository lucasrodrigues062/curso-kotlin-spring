package dev.nsb.clinica.patient.search

import dev.nsb.clinica.patient.Patient
import dev.nsb.clinica.patient.PatientRepository
import dev.nsb.clinica.patient.register.RegisterPatientDto
import dev.nsb.clinica.shared.validation.ExistsId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/v1/paciente")
class SearchPatientController(
    val repository: PatientRepository,
    val existsId: ExistsId
) {

    @GetMapping("/{id}")
    fun searchById(
        @PathVariable(name = "id")
        id: UUID
    ): ResponseEntity<Any>{
        existsId.check(klass = Patient::class, fieldName = "externalId", value = id)

        val patient = repository.findByExternalIdEquals(id).get()

        return ResponseEntity.ok(RegisterPatientDto(patient))
       // return ResponseEntity.ok().build()

    }
}
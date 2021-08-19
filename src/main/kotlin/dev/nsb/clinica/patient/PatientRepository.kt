package dev.nsb.clinica.patient;

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PatientRepository : JpaRepository<Patient, Long> {

    fun findByExternalIdEquals(externalId: UUID): Optional<Patient>

}
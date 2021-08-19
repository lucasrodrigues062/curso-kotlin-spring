package dev.nsb.clinica.professional;

import org.springframework.data.jpa.repository.JpaRepository

interface ProfessionalRepository : JpaRepository<Professional, Long> {
}
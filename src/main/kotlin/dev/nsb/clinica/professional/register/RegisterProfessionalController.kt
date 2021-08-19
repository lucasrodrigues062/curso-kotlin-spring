package dev.nsb.clinica.professional.register

import dev.nsb.clinica.professional.ProfessionalRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

@RestController
class RegisterProfessionalController(
    val repository: ProfessionalRepository
) {
    @PostMapping("/api/v1/profissional")
    fun register(
        @RequestBody @Valid professionalDto: RegisterProfessionalDto,
        builder: UriComponentsBuilder
    ): ResponseEntity<Any>{

        val professional = repository.save(professionalDto.toModel())
        val uri = builder.path("/api/v1/profissional/${professional.externalId}").build().toUri()

        return ResponseEntity.created(uri).body(RegisterProfessionalDto(professional))
    }

}
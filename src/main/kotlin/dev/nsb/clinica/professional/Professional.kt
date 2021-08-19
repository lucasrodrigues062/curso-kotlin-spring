package dev.nsb.clinica.professional

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*


@Entity
class Professional(
    val name:String,
    val email: String,
    val phone: String,
    val document: String,
    @ElementCollection(fetch = FetchType.LAZY, targetClass = Specialty::class)
    @Enumerated(EnumType.STRING)
    //@JoinTable(name = "professional_specialty")
    @CollectionTable(name = "professional_specialty")
    val specialty: List<Specialty>,
    val externalId: UUID = UUID.randomUUID(),
    val registryDate: LocalDateTime = LocalDateTime.now()
) {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long? = null

}
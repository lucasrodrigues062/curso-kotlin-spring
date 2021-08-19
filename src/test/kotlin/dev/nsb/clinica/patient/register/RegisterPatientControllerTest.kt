package dev.nsb.clinica.patient.register

import com.fasterxml.jackson.databind.ObjectMapper
import dev.nsb.clinica.patient.PatientRepository
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
internal class RegisterPatientControllerTest {
    @Autowired
    lateinit var controller: RegisterPatientController
    @Autowired
    lateinit var repository: PatientRepository
    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var jsonMapper: ObjectMapper
   // lateinit var
   // Necessario para mockar classes final https://www.baeldung.com/mockito-final

    @BeforeEach
    fun setup(){
        RestAssuredMockMvc.standaloneSetup(this.controller)
        repository.deleteAll()
    }
    @AfterEach
    fun clean(){
        repository.deleteAll()
    }

    @Test
    fun `should register a new Patient`(){

        val result = mockMvc.perform(post("/api/v1/paciente")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json(generateRequestPatientDto())))
            .andExpect(status().isCreated)
            .andReturn()
        assertTrue( result.response.contentAsString.contains("70373330006"))

        val table = repository.findAll()
        assertTrue(table.size == 1)

    }

    @Test
    fun `should not register a new Patient with the same document`(){
        repository.save(generateRequestPatientDto().toModel())

        val result = mockMvc.perform(post("/api/v1/paciente")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json(generateRequestPatientDto())))
            .andExpect(status().isUnprocessableEntity)
            .andReturn()
        assertTrue( result.response.contentAsString.contains("Already There is a document with this value"))

        val table = repository.findAll()
        assertTrue(table.size == 1)
    }

    @Test
    fun `should not register a new Patient with invalid data`(){
        val patient = generateRequestPatientDto(name = "", email = "mail.com", document = "12345678910", phone = "99999999", birthDate = LocalDate.now())

         mockMvc.perform(post("/api/v1/paciente")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json(patient)))
            .andExpect(status().isBadRequest)

        val table = repository.findAll()
        assertTrue(table.isEmpty())
        //assertTrue( result.response.contentAsString.contains("Already There is a document with this value"))
    }

    @Test
    fun `should not register a new Patient with invalid request`() {

        mockMvc.perform(post("/api/v1/paciente")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
            .andExpect(status().isBadRequest)

        val table = repository.findAll()
        assertTrue(table.isEmpty())
    }


    private fun generateRequestPatientDto(
        name:String = "Test Patient",
        email:String = "test@email.com",
        document:String = "70373330006",
        phone:String = "+5511999999999",
        birthDate:LocalDate = LocalDate.of(
            1990, 1,1
        )
    ): RegisterPatientDto {

            return RegisterPatientDto(
                name, email, document, phone, birthDate
            )
    }

    private fun json( patientDto: RegisterPatientDto): String {
        return jsonMapper.writeValueAsString(patientDto)
    }
}
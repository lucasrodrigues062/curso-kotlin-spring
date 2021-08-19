package dev.nsb.clinica.patient.register

import dev.nsb.clinica.patient.Patient
import dev.nsb.clinica.patient.PatientRepository
import dev.nsb.clinica.shared.validation.UniqueValueValidator
import io.restassured.http.ContentType
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import java.time.LocalDate
import javax.persistence.EntityManager
import javax.transaction.Transactional
import javax.validation.ConstraintValidatorContext

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
//@WebMvcTest(RegisterPatientController::class)
internal class RegisterPatientControllerTest(

    @Autowired
    val controller: RegisterPatientController,
    @Autowired
    val repository: PatientRepository,



) {
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
//        given(this.uniqueValueValidator.isValid(
//            generateRequestPatientDto().document,
//            null
//        ))
//            .willReturn(true)
//        `when`(this.uniqueValueValidator.isValid(generateRequestPatientDto().document,null)).thenReturn(true)

        val httpRequest = RestAssuredMockMvc.given().body(generateRequestPatientDto())
            .contentType(ContentType.JSON)
        val response = httpRequest.`when`()
            .post("/api/v1/paciente")

        val responseBody = response.body.asString()


        Assertions.assertEquals(200, response.statusCode)
        Assertions.assertTrue(responseBody.contains("Test Patient"))
       // assertTrue(true)
    }

    private fun generateRequestPatientDto(
        name:String = "Test Patient",
        email:String = "test@email.com",
        document:String = "70373330006",
        phone:String = "+5511999999999",
        birthDate:LocalDate = LocalDate.of(
            1990, 1,1
        )
    ): Patient {

            return Patient(
                name, email, document, phone, birthDate
            )
    }
}
package dev.nsb.clinica.patient.search

import dev.nsb.clinica.patient.Patient
import dev.nsb.clinica.patient.PatientRepository
import dev.nsb.clinica.shared.exceptions.ExistsIdException
import io.restassured.module.mockmvc.RestAssuredMockMvc
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.util.NestedServletException
import java.time.LocalDate
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
//@WebMvcTest(SearchPatientController::class)
//@Transactional
internal class SearchPatientControllerTest(

    @Autowired
    val controller: SearchPatientController,
    @Autowired
    val repository: PatientRepository
) {

    companion object {
        val randomUUID = UUID.randomUUID()
    }

    @BeforeEach
    fun setup(){
        RestAssuredMockMvc.standaloneSetup(this.controller)
        repository.save(generatePatient())
    }
    @AfterEach
    fun clean(){
        repository.deleteAll()
    }

    //https://blog.jayway.com/2014/01/14/unit-testing-spring-mvc-controllers-with-rest-assured/
    @Test
    fun `should return a patient`(){
// exemplo de rest Assured, multiplas validacoes: https://www.youtube.com/watch?v=-wW_e4aoozI
        val httpRequest = RestAssuredMockMvc.given()
        val response = httpRequest.get("/api/v1/paciente/${randomUUID}")
        val responseBody = response.body.asString()


        assertEquals(200, response.statusCode)
        assertTrue(responseBody.contains(randomUUID.toString()))
    }

    @Test
    fun `sould not return a patient where UUID does not exist`() {
        val httpRequest = RestAssuredMockMvc.given()
        assertThrows<NestedServletException> { val response = httpRequest.get("/api/v1/paciente/${UUID.randomUUID()}").mockHttpServletResponse }

    }


    private fun generatePatient(): Patient{
        return Patient(
            name = "Test Patient",
            email = "test@email.com",
            document = "70373330006",
            phone = "+5511999999999",
            birthDate = LocalDate.of(
                1990, 1,1
            ),
            externalId = randomUUID

        )
    }
}
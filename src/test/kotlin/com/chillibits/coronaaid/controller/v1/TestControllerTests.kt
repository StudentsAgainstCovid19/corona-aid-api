package com.chillibits.coronaaid.controller.v1

import com.chillibits.coronaaid.exception.exception.InfectedNotFoundException
import com.chillibits.coronaaid.exception.exception.InvalidTestResultException
import com.chillibits.coronaaid.model.db.Infected
import com.chillibits.coronaaid.model.db.Test
import com.chillibits.coronaaid.model.dto.TestDto
import com.chillibits.coronaaid.model.dto.TestInsertDto
import com.chillibits.coronaaid.repository.InfectedRepository
import com.chillibits.coronaaid.repository.TestRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalDate
import java.time.Month
import java.util.*

@RunWith(SpringRunner::class)
@ActiveProfiles("logging")
@DisplayName("Test Controller")
class TestControllerTests {

    @Autowired
    private lateinit var testController: TestController

    @MockBean
    private lateinit var testRepository: TestRepository

    @MockBean
    private lateinit var infectedRepository: InfectedRepository

    private val testBirthDate = LocalDate.now()
    private val testTimestamp = System.currentTimeMillis()
    private val testData = getTestData()
    private val assertData = getAssertData()

    @TestConfiguration
    class TestControllerImplTestContextConfiguration {

        @Bean
        fun testController() = TestController()
    }

    @Before
    fun init() {
        // Setup fake function calls
        // Test repository
        Mockito.`when`(testRepository.findAll()).thenReturn(testData.toList())
        Mockito.`when`(testRepository.findTestsForPerson(testData.elementAt(0).infectedId!!.id)).thenReturn(setOf(testData.elementAt(0), testData.elementAt(3)))
        Mockito.`when`(testRepository.save(getPostRequiredTestSaveInput())).thenReturn(getPostRepositorySaveOutput())

        // Infected repository
        Mockito.`when`(infectedRepository.findById(5)).thenReturn(Optional.of(getDummyInfected()))
        Mockito.`when`(infectedRepository.findById(-10)).thenReturn(Optional.empty())
    }

    // ---------------------------------------------------- Tests ------------------------------------------------------

    @org.junit.Test
    @DisplayName("Test for getting all tests - success")
    fun testGetAllTests() {
        val result = testController.getAllTests()
        assertThat(result).containsExactlyInAnyOrder(assertData.elementAt(0), assertData.elementAt(1), assertData.elementAt(2), assertData.elementAt(3), assertData.elementAt(4))
    }

    @org.junit.Test
    @DisplayName("Test for getting all tests from single person - success")
    fun testGetTestsFromForPerson() {
        val result = testController.getTestsForSinglePerson(testData.elementAt(0).infectedId!!.id)
        assertEquals(setOf(assertData.elementAt(0), assertData.elementAt(3)), result)
    }

    @org.junit.Test
    @DisplayName("Test for pushing test into db - success")
    fun testAddTest() {
        val result = testController.addTest(getPostInputDto())
        assertEquals(result, getPostExpectedPostOutputDto())
    }

    @org.junit.Test
    @DisplayName("Test for pushing test into db - success")
    fun testAddTestUnknownInfected() {
        assertThrows<InfectedNotFoundException> { testController.addTest(getPostUnknownInfected()) }
    }

    @org.junit.Test
    @DisplayName("Test for pushing test into db - success")
    fun testAddTestInvalidResult() {
        assertThrows<InvalidTestResultException> { testController.addTest(getPostInvalidTestResult()) }
    }

    // -------------------------------------------------- Test data ----------------------------------------------------

    private fun getTestData(): Set<Test> {
        val infected1 = Infected(0, "John", "Doe", testBirthDate, "Karlsruhe", "76131",
                                "Erzbergerstraße", "121", 49.0264134, 8.3831085, "M654321",
                                lockedTimestamp = testTimestamp)
        val infected2 = Infected(1, "Joe", "Dalton", testBirthDate, "Mannheim", "68159",
                                "Göthestraße", "4", 49.4874639, 8.4763718, "M654321",
                                lockedTimestamp = testTimestamp)

        // Tests
        val test1 = Test(0, infected1, testTimestamp, 0)
        val test2 = Test(1, infected2, testTimestamp, 2)
        val test3 = Test(2, infected2, testTimestamp, 3)
        val test4 = Test(3, infected1, testTimestamp, 1)
        val test5 = Test(4, null, testTimestamp, 1)
        return setOf(test1, test2, test3, test4, test5)
    }

    private fun getAssertData(): Set<TestDto> {
        // Tests
        val test1 = TestDto(0, 0, testTimestamp, 0)
        val test2 = TestDto(1, 1, testTimestamp, 2)
        val test3 = TestDto(2, 1, testTimestamp, 3)
        val test4 = TestDto(3, 0, testTimestamp, 1)
        val test5 = TestDto(4, null, testTimestamp, 1)
        return setOf(test1, test2, test3, test4, test5)
    }

    private fun getDummyInfected()
            = Infected(5, "Angelo", "Merte", LocalDate.of(1800, Month.JANUARY, 10),
            "Berlin", "11111", "Nuclearstreet", "666", 420.0, 360.0,
            healthInsuranceNumber = "M999999", lockedTimestamp = 54321L)

    private fun getPostInputDto()
            = TestInsertDto(5, 12345, 0)
    private fun getPostRequiredTestSaveInput()
            = Test(0, getDummyInfected(), 12345, 0)
    private fun getPostRepositorySaveOutput()
            = Test(999, getDummyInfected(), 12345, 0)
    private fun getPostExpectedPostOutputDto()
            = TestDto(999, getDummyInfected().id, 12345, 0)
    private fun getPostUnknownInfected()
            = TestInsertDto(-10, 12345, 0)
    private fun getPostInvalidTestResult()
            = TestInsertDto(-10, 12345, 2)
}
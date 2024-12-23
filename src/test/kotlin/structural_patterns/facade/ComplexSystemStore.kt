package structural_patterns.facade

import kotlin.test.Test
import kotlin.test.assertEquals

class ComplexSystemStore(private val filePath: String) {
    private val cache: MutableMap<String, String>

    init {
        println("Reading data from file: $filePath")
        cache = mutableMapOf()
        //Read properties from file and put to cache
    }

    fun store(key: String, value: String) {
        cache[key] = value
    }

    fun read(key: String) = cache[key] ?: ""

    fun commit() = println("Storing cached data to file: $filePath")
}

data class User(val email: String)

//Facade
class UserRepository {
    private val systemPreferences = ComplexSystemStore("/data/default.prefs")

    fun save(user: User) {
        systemPreferences.store("USER_KEY", user.email)
        systemPreferences.commit()
    }

    fun findFirst(): User = User(systemPreferences.read("USER_KEY"))
}

const val emailTest = "user1@test.com"

class FacadeTest {
    @Test
    fun testFacade() {
        val userRepository = UserRepository()
        val user = User(email = emailTest)
        userRepository.save(user)

        val foundUser = userRepository.findFirst()
        println("Found user: ${foundUser.email}")

        assertEquals(expected = emailTest, actual = foundUser.email)
    }
}

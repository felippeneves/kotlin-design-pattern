package behavioural_patterns.state

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

sealed class AuthorizationState

data object Unauthorized : AuthorizationState()

class Authorized(val username: String) : AuthorizationState()

class AuthorizationService {
    private var state: AuthorizationState = Unauthorized

    fun login(username: String) {
        println("User: $username logged in")
        state = Authorized(username)
    }

    fun logout() {
        println("User: ${getUsername()} logged out")
        state = Unauthorized
    }

    fun isAuthorized(): Boolean {
        return state is Authorized
    }

    fun getUsername(): String {
        return (state as? Authorized)?.username ?: "Unknown"
    }

    override fun toString() = "User ${getUsername()} is logged in: ${isAuthorized()}"
}

const val ADMIN = "admin"

class StateTest {
    @Test
    fun testState() {
        val authorizationService = AuthorizationService()
        println(authorizationService)

        println("-----------------------")

        authorizationService.login(username = ADMIN)
        assertTrue(authorizationService.isAuthorized())
        assertEquals(expected = ADMIN, actual = authorizationService.getUsername())
        println(authorizationService)

        println("-----------------------")

        authorizationService.logout()
        println(authorizationService)
    }
}

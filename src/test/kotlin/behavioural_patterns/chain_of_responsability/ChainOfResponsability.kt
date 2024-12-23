package behavioural_patterns.chain_of_responsability

import kotlin.test.Test
import kotlin.test.assertEquals

interface HandlerChain {
    fun addHeader(inputHeader: String): String
}

class AuthenticationHeader(private val token: String?, var next: HandlerChain? = null) : HandlerChain {
    override fun addHeader(inputHeader: String) =
        "$inputHeader\nAuthorization: $token"
            .let { next?.addHeader(it) ?: it }
}

class ContentTypeHeader(private val contentType: String, var next: HandlerChain? = null) : HandlerChain {
    override fun addHeader(inputHeader: String) =
        "$inputHeader\nContent-Type: $contentType"
            .let { next?.addHeader(it) ?: it }
}

class BodyPayloadHeader(private val body: String, var next: HandlerChain? = null) : HandlerChain {
    override fun addHeader(inputHeader: String) =
        "$inputHeader\nBody: $body"
            .let { next?.addHeader(it) ?: it }
}

class ChainOfResponsabilityTest {
    @Test
    fun testChainOfResponsability() {
        val authenticationHeader = AuthenticationHeader(token = "123456")
        val contentTypeHeader = ContentTypeHeader(contentType = "application/json")
        val bodyPayloadHeader = BodyPayloadHeader(body = "{\"name\": \"Felippe\"}")

        authenticationHeader.next = contentTypeHeader
        contentTypeHeader.next = bodyPayloadHeader

        val messageWithAuthenticationHeader =
            authenticationHeader.addHeader(inputHeader = "Headers with authentication")
        println(messageWithAuthenticationHeader)

        println("-------------------")

        val messageWithoutAuthentication =
            contentTypeHeader.addHeader(inputHeader = "Headers without authentication")
        println(messageWithoutAuthentication)

        assertEquals(
            expected = """
                    Headers with authentication
                    Authorization: 123456
                    Content-Type: application/json
                    Body: {"name": "Felippe"}
                """.trimIndent(),
            actual = messageWithAuthenticationHeader
        )
        assertEquals(
            expected = """
                    Headers without authentication
                    Content-Type: application/json
                    Body: {"name": "Felippe"}
                """.trimIndent(),
            actual = messageWithoutAuthentication
        )
    }
}

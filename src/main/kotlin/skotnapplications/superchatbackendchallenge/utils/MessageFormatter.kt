package skotnapplications.superchatbackendchallenge.utils

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.client.RestTemplate
import skotnapplications.superchatbackendchallenge.model.Message
import skotnapplications.superchatbackendchallenge.repository.ContactRepository
import java.net.URI
import java.util.regex.Matcher
import java.util.regex.Pattern


@Component
class MessageFormatter {

    @Value("\${bitcoin}")
    private lateinit var bitcoinUrl: URI

    private lateinit var message: Message

    @Autowired
    private lateinit var contactRepository: ContactRepository

    @Autowired
    private lateinit var restTemplate: RestTemplate

    var formatterMap = mapOf(
        "{recipent_name}" to { getRecipentName() },
        "{bitcoin_usd}" to { getBitcoinPrice() },
        "{greeting}" to { "Hello wassup" }
    )

    fun formatMessage(message: Message): String {
        this.message = message
        var text = message.content

        val p: Pattern = Pattern.compile("\\{([^}]*)\\}")
        val m: Matcher = p.matcher(text)
        while (m.find()) {
            val keyValue = m.group()
            text = text.updatedText(keyValue)
        }
        return text
    }

    private fun String.updatedText(keyValue: String) =
        if (formatterMap.containsKey(keyValue)) this.replace(keyValue, formatterMap.getValue(keyValue).invoke())
        else this


    private fun getRecipentName(): String {
        val contact = contactRepository.findById(this.message.receiver)
        if (contact.isPresent) return contact.get().name
        throw HttpClientErrorException(HttpStatus.NOT_FOUND, "Couldn't find recipient contact info")
    }

    private fun getBitcoinPrice(): String {
        return try {
            val response: ResponseEntity<JsonNode> = restTemplate.exchange(
                bitcoinUrl, HttpMethod.GET, null,
                JsonNode::class.java
            )
            response.body!!.get("data").get("amount").textValue() + " USD"
        } catch (e: Exception) {
            throw HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Couldn't get bitcoin price")
        }
    }
}



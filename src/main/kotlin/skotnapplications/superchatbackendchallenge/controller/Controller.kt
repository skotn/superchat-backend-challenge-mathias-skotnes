package skotnapplications.superchatbackendchallenge.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import skotnapplications.superchatbackendchallenge.model.*
import skotnapplications.superchatbackendchallenge.repository.ContactRepository
import skotnapplications.superchatbackendchallenge.repository.MessageRepository
import skotnapplications.superchatbackendchallenge.utils.MessageFormatter

@RestController
class Controller {

	@Autowired
	lateinit var messageRepository: MessageRepository

	@Autowired
	lateinit var contactRepository: ContactRepository

	@Autowired
	lateinit var messageFormatter: MessageFormatter

	@GetMapping("/contacts")
	fun getAllContacts(): ResponseEntity<List<Contact>> {
		val contacts = contactRepository.findAll()
		return ResponseEntity(contacts, HttpStatus.OK)
	}

	@PostMapping("/messages")
	fun postMessage(@RequestBody message: Message): ResponseEntity<Message> {
		val messageText = messageFormatter.formatMessage(message)
		val createdMessage = messageRepository.save(Message(message.sender, message.receiver, messageText))
		return ResponseEntity(createdMessage, HttpStatus.CREATED)
	}

	@PostMapping("/contacts")
	fun createContact(@RequestBody contact: Contact): ResponseEntity<Contact> {
		val createdContact = contactRepository.save(Contact(contact.name, contact.mail))
		return ResponseEntity(createdContact, HttpStatus.CREATED)
	}
}
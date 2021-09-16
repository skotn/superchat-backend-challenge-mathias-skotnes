package skotnapplications.superchatbackendchallenge.repository

import org.springframework.data.jpa.repository.JpaRepository
import skotnapplications.superchatbackendchallenge.model.Contact
import skotnapplications.superchatbackendchallenge.model.Message

interface ContactRepository : JpaRepository<Contact, Long>

interface MessageRepository : JpaRepository<Message, Long>
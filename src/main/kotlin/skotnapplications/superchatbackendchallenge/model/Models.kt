package skotnapplications.superchatbackendchallenge.model

import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Contact(
    val name: String,
    val mail: String,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null)

@Entity
class Message(
    var sender: Long,
    var receiver: Long,
    var content: String,
    val dateTime: LocalDateTime = LocalDateTime.now(),
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null)


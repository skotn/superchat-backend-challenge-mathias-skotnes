package skotnapplications.superchatbackendchallenge.model

class Conversation(
    val receiver: Long,
    val messages: List<Message>
)
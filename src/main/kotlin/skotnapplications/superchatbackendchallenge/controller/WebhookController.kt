package skotnapplications.superchatbackendchallenge.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class WebhookController {

    @PostMapping("/webhook")
    fun RetrieveWebook(requestBody: HttpServletRequest): String {
        //TODO: Do something when the hook comes in
        return "ok"
    }
}
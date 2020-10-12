package veersingh.veerchatbot.utils

import veersingh.veerchatbot.utils.Constants.OPEN_G
import veersingh.veerchatbot.utils.Constants.OPEN_PHD
import veersingh.veerchatbot.utils.Constants.OPEN_UG
import veersingh.veerchatbot.utils.Constants.OPEN_WEBPAGE

object BotResponse {

    fun basicResponses(_message: String): String {
        val message = _message.toLowerCase()

        return when{

            //Undergraduate
            message.contains("undergraduate") || message.contains("bachelors") -> OPEN_UG

            //Graduate
            message.contains("graduate") || message.contains("masters") -> OPEN_G

            //PhD
            message.contains("phd") || message.contains("doctarate") -> OPEN_PHD

            //Webpage
            message.contains("website") -> OPEN_WEBPAGE

            else -> "Please type one of the options from the list! "
        }
    }
}
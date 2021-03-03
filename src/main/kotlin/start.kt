package KOTLIN

import java.io.*
import java.net.ServerSocket
import java.util.*

/*
super prosty server http
opanowane żądanie i odpowiedź oraz kodowanie znaków
całość w Kotlinie
 */


//metoda startowa

fun main() {

    var x = 1

    //zmienne
    val PORT = Integer.parseInt(System.getProperty("port", "8080"))
    val newLine = "\r\n"
    try {
        val serverSocket = ServerSocket(PORT) // obiekt servera
        println("serwer startuje na porcie $PORT")
        while (true) {
            val client = serverSocket.accept() // klienckie połączenie, cały czas nasłuch
            try {
                // odbiór danych
                val input = BufferedReader(InputStreamReader(client.getInputStream()))
                val out = BufferedOutputStream(client.getOutputStream())
                val pout = PrintStream(out)

                // czytamy dane żądania
                val request = input.readLine()
                println("zawartość żądania: $request")


                //1. wysyłamy testową odpowiedź do klienta
                val response = "<h1>odpowiedź do klienta nr $x</h1> "
                x++
                //jeśłi poszła dobrze, to dalej działąmy z ponizszym kodem

                //2. odpowiedź w postaci JSON-a
                //https://developer.mozilla.org/en-US/docs/Web/HTTP/Messages
                //https://mkyong.com/computer-tips/how-to-view-http-headers-in-google-chrome/
                //https://developer.mozilla.org/en-US/docs/Web/HTTP/Status

                // testujemy w przegladarce i postmanie

                //val jsonBody = "{a:1, b:2}"
                pout.print(
                        "HTTP/1.1 200 OK" + newLine +
                                "Content-Type: text/html; charset=UTF-8" + newLine +
                                "Date: " + Date() + newLine +
                                "Content-length: " + response.length
                                + newLine
                                + newLine +
                                response
                )
                pout.close()


            } catch (e: Throwable) {
                println("problem z odebraniem danych na serwerze $e")
            }
        }
    } catch (e: Throwable) {
        println("problem z uruchomieniem serwera $e")
    }
} //pozostałe metody

package fr.polytech.marsy.webcasterservice.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class SocketCom {
    private static final Logger LOGGER = Logger.getLogger(SocketCom.class.getSimpleName());

    final static String ROCKET_SERVICE_URL = "http://rocket-service:8080";

    private final RestTemplate restTemplate = new RestTemplate();

    public void startCommunication() {
        List<String> questions = Arrays.asList(
                "Hello Thomas, how are you?",
                "How is it up there?",
                "Ok have fun!"
        );

        try {
            // create socket server on port 3000
            LOGGER.info("Webcaster creating a socket server on port 3000");
            ServerSocket serverSocket = new ServerSocket(3000);
            restTemplate.put(ROCKET_SERVICE_URL + "/startInterview", null);
            LOGGER.info("We are currently trying to establish a connection with Thomas Pasquier");
            Socket clientSocket = serverSocket.accept();
            LOGGER.info("We finally established a connection with Thomas!");

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // send questions to Thomas
            for (String question : questions) {
                Thread.sleep(2000);
                LOGGER.info("Ms Caster says: " + question);
                // send question in socket
                out.println(question);
                // display received answer
                LOGGER.info("Thomas Pasquier says: " + in.readLine());
            }

            LOGGER.info("We are done talking to Thomas, closing the connection");
            out.println("close");
            out.close();
            in.close();
            clientSocket.close();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error during socket communication:", e);
            e.printStackTrace();
        }
    }
}

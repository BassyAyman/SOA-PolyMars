package com.marsy.teamb.rocketservice.services;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class SocketCom {
    private static final Logger LOGGER = Logger.getLogger(SocketCom.class.getSimpleName());

    public void startCommunication() {
        Map<String, String> responses = new HashMap<>();
        responses.put("Hello Thomas, how are you?", "Hello Ms Caster, I'm fine thank you");
        responses.put("How is it up there?", "It's quite empty to be honest");
        responses.put("Ok have fun!", "I will, bye!");

        try {
            LOGGER.info("Rocket trying to connect to webcaster-service");
            Socket socket = new Socket("webcaster-service", 3000);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.equals("close")) {
                    LOGGER.info("Closing the connection");
                    out.close();
                    in.close();
                    socket.close();
                    break;
                }
                LOGGER.info("Ms Caster says: " + inputLine);
                Thread.sleep(2000);
                String response = responses.get(inputLine);
                if (response != null) {
                    LOGGER.info("Thomas Pasquier says: " + response);
                    out.println(response);
                } else {
                    out.println("Thomas Pasquier says: I can't here you well.");
                }
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error during socket communication:", e);
            e.printStackTrace();
        }
    }
}

package chatsystem;

import chatsystem.controller.UDPController;
import chatsystem.network.udp.UDPSender;
import chatsystem.ui.ChooseUsernameGUI;
import chatsystem.ui.View;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;

import java.net.UnknownHostException;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);
	
    public static void main(String[] args) throws InterruptedException, UnknownHostException {    	
    	LOGGER.trace("Current IP " + UDPSender.getAllCurrentIp());
        Configurator.setRootLevel(Level.INFO);
        LOGGER.info("Starting ChatSystem application");

        ChooseUsernameGUI.initialize();
        //Controller TCPListener start goes here
    }
}
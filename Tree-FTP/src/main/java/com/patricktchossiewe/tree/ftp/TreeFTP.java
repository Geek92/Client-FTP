/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.patricktchossiewe.tree.ftp;

import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author patrickfrank
 */
public class TreeFTP {

    public static void main(String[] args) {
        
        String servername = "";
        String username = "";
        String password = "";
        int port = 21;
        
        if(args.length > 0){
            if(args.length >= 3){
                username = args[1];
                password = args[2];
            }
            servername = args[0];
        } else {
                System.out.println("Inserez le nom du serveur!\n"+"inserez le nom d'utilisateur(optionnel)\n"+"inserez le mot de passe(optionnel)");
                return;
        }
        
        //instanciation de la classe ServerConnection
        ServerConnection serverConnection = new ServerConnection(servername, port);
        
        //connection au serveur
        serverConnection.connect();
        
        //on effectue le logging et on affiche le repertoir actuel
        String userCommand = "USER "+username;
        String passwordCommand = "PASS "+password;
        String currentDirectory = "PWD";
        String binnaryModeCommand = "TYPE I";  
        String passiveCommand = "PASV";
        String listCommand = "LIST";
        
        List<String> controlCommands = new ArrayList();
        List<String> dataCommands = new ArrayList();
        
        controlCommands.add(userCommand);
        controlCommands.add(passwordCommand);
        controlCommands.add(currentDirectory);
        controlCommands.add(binnaryModeCommand);
        controlCommands.add(passiveCommand);
        
        dataCommands.add(listCommand);
        
        for (String command : controlCommands) {
            serverConnection.sendCommand(command);
        }
        
        serverConnection.opendataConnection();
        
        for (String dataCommand : dataCommands) {
            serverConnection.sendCommand(dataCommand);
        }
 
        //on ferme la connection
        serverConnection.closeConnection();
    }
}

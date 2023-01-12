/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.patricktchossiewe.tree.ftp;

import java.util.ArrayList;


/**
 *
 * @author patrickfrank
 */
public class TreeFTP {

    public static void main(String[] args) {
        
        String servername = "ftp.ubuntu.com";
        int port = 21;
        
        //instanciation de la classe ServerConnection
        ServerConnection serverConnection = new ServerConnection(servername, port);
        
        //connection au serveur
        serverConnection.connect();
        
        //on recupere et on affiche la reponse du serveur
        ArrayList<String> responses = serverConnection.readDataFromServer();
        printServerResponse(responses);
        
        //on envoie la commande ensuite on recupere la reponse et on affiche
        String command = "USER anonymous";
        serverConnection.sendCommand(command);
        ArrayList<String> responses_command = serverConnection.readDataFromServer();
        printServerResponse(responses_command);
        
        //on ferme la connection
        serverConnection.closeConnection();
            
    }
    
    static void printServerResponse(ArrayList<String> responses){
    
        for (String response : responses) {
            System.out.println("reponse du serveur: "+response);  
        }
    }
}

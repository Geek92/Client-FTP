/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.patricktchossiewe.tree.ftp;



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
        
        serverConnection.sendCommand(userCommand);
        serverConnection.sendCommand(passwordCommand);
        serverConnection.sendCommand(currentDirectory);
 
        //on ferme la connection
        serverConnection.closeConnection();
    }
}

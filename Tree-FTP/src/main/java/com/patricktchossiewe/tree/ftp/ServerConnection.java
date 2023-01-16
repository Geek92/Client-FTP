/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.patricktchossiewe.tree.ftp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


/**
 *
 * @author patrickfrank
 */
public class ServerConnection {
    
    private final String server;
    private final int port;
    private Socket clientSocket;
    private BufferedReader reader;
    private BufferedWriter writer;
    

    public ServerConnection(String server, int port) {
        this.server = server;
        this.port = port;
    }
    /**
     *  cette methode permets de se connecter au serveur
     */
    public void connect(){
        try {
                clientSocket = new Socket(server, port);
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                printServerRespondse();
             
        } catch (IOException ex) {
                System.out.println("erreur lors de l'ouverture de la connection");
        }
    }
    
    /**
     * cette methode permets d'envoyer une commande au serveur et d'afficher la reponse
     * @param command 
     */
    public void sendCommand(String command){
        try {
                writer.write(command);
                writer.newLine();
                writer.flush();
                printServerRespondse();
        } catch (IOException e) {
                System.out.println("impossible d'envoyer la commande au serveur");
        }
    }
    
    /**
     * cette methode permets d'afficher la reponse du serveur
     */
    public void printServerRespondse(){
        try {
                System.out.println( reader.readLine());
        } catch (IOException ex) {
                System.out.println("erreur lors de la lecture");
        }
    }

    /**
     * cette methode permets de fermer la connection avec le serveur
     */
   public void closeConnection(){
        try {
                reader.close();
                writer.close();
                clientSocket.close();
        } catch (IOException ex) {
                System.out.println("erreur lors de la fermeture de la connection");
        }
   }
}

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
    private Socket controlSocket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private int dataPort;
    private Socket dataSocket;
    private String passvResponse;
    private BufferedReader dataReader;
    private BufferedWriter dataWriter;
    
    

    public ServerConnection(String server, int port) {
        this.server = server;
        this.port = port;
    }
    /**
     *  cette methode permets de se connecter au serveur
     */
    public void connect(){
        try {
                controlSocket = new Socket(server, port);
                reader = new BufferedReader(new InputStreamReader(controlSocket.getInputStream()));
                writer = new BufferedWriter(new OutputStreamWriter(controlSocket.getOutputStream()));
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
        String result = "";
        try {
             switch(command){
                 case "PASV":
                    writer.write(command);
                    writer.newLine();
                    writer.flush();
                    passvResponse = printServerRespondse(); 
                     System.out.println(passvResponse);
                    break;
                 
                 case "LIST":
                     writer.write(command);
                     writer.newLine();
                     writer.flush();
                     while((result = dataReader.readLine()) != null){
                         System.out.println(dataReader.readLine());
                     }
                     break;
                     
                default:
                    writer.write(command);
                    writer.newLine();
                    writer.flush();
                    System.out.println(printServerRespondse());
             }
                
        } catch (IOException e) {
                System.out.println("impossible d'envoyer la commande au serveur");
        }
    }
    
    /**
     * cette methode permets d'afficher la reponse du serveur
     */
    public String printServerRespondse(){
        String result = "";
        try {
               result = reader.readLine();
        } catch (IOException ex) {
                System.out.println("erreur lors de la lecture");
        }
        
        return result;
    }
    /**
     * fonction qui permets d'ouvrir une connection de données avec lec serveur
     */
    public void opendataConnection(){
        
        String[] splitValues = passvResponse.split(" ");
        String[] portValues = splitValues[4].split(",");
        int value1, value2;
        
        value1 = Integer.parseInt(portValues[4]);
        value2 = Integer.parseInt(portValues[5].substring(0,portValues[5].length() - 2));
        
        dataPort = (value1 * 256) + value2;

        try {
            dataSocket = new Socket(server,dataPort);
            dataReader = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
            dataWriter = new BufferedWriter(new OutputStreamWriter(controlSocket.getOutputStream()));
        } catch (IOException ex) {
            System.out.println("impossible d'ouvrir une connection de données!");
        }
        
    }
    /**
     * cette methode permets de fermer la connection avec le serveur
     */
   public void closeConnection(){
        try {
                reader.close();
                writer.close();
                controlSocket.close();
        } catch (IOException ex) {
                System.out.println("erreur lors de la fermeture de la connection");
        }
   }
}

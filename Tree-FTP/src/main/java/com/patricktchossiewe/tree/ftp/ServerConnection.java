/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.patricktchossiewe.tree.ftp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;


/**
 *
 * @author patrickfrank
 */
public class ServerConnection {
    
    private String ipAddress;
    private int port;
    private Socket clientSocket;

    public ServerConnection(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }
    /**
     * this method is used to create the Socket and connect to the specified server
     */
    public void connect(){
         
        try {
             clientSocket = new Socket(ipAddress, port);
            
        } catch (IOException ex) {
             System.out.println("erreur lors de l'ouverture de la connection");
        }
    }
    /**
     * tihs method is used to read data from server
     * @return retruns an Arraylist that contain the response of the server
     */
    public ArrayList<String> readDataFromServer(){
        
        ArrayList<String> serverResponse = new ArrayList();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String line;
            while((line = reader.readLine())!= null){
               serverResponse.add(line);
            }
            reader.close();
        } catch (IOException ex) {
            System.out.println("erreur lors de la lecture");
        }
            return serverResponse;
    }
    
    public void sendCommand(String command){
    
        PrintStream out = null;
        try {
            out = new PrintStream(clientSocket.getOutputStream());
            out.println(command);
        } catch (IOException ex) {
            System.out.println("erreur lors de l'envoie de la commande "+command);
        } finally {
            out.close();
        }
    }
    
    
    /**
     * this methosd is used to close the connection between server and our client
     */
   public void closeConnection(){
        try {
            clientSocket.close();
        } catch (IOException ex) {
             System.out.println("erreur lors de la fermeture de la connection");
        }
   }
}

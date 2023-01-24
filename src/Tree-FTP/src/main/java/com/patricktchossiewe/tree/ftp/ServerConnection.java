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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;


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
    private Stack stack;
    private Set<String> visited;
    private Set<String> subFiles_or_subDirectories;
    private List<String> dataresponses;
    
    
    

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
                printConnectionServerResponses();
             
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
                        passvResponse = printConnectionServerResponses(); 
                        System.out.println(passvResponse);
                        break;
                 
                 case "LIST":
                     writer.write(command);
                     writer.newLine();
                     writer.flush();
                     dataresponses =  printDataServerResponse();
                     break;
                     
                default:
                    writer.write(command);
                    writer.newLine();
                    writer.flush();
                    System.out.println(printConnectionServerResponses());
             }
                
        } catch (IOException e) {
                System.out.println("impossible d'envoyer la commande au serveur");
        }
    }

    /**
     *  cette methode permets d'afficher la reponse du serveur
     * @return List<String>
     */
    public List<String> printDataServerResponse(){
        List<String> results = new ArrayList();
        String line = "";
        try {
               while((line = dataReader.readLine())!= null){
                   results.add(line);
               }
        } catch (IOException ex) {
                System.out.println("erreur lors de la lecture des données");
        }
        return results;
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
     * cette fonction permets d'afficher la reponse du serveur a une commande de données
     * @return String
     */
    
    public String printConnectionServerResponses(){
        String line = "";
        
        try {
            line = reader.readLine();
        } catch (IOException e) {
            System.out.println("impossible de lire la reponse du serveur");
        }
        
        return line;
    }
    
    /**
     * cette methode nous permets d'afficher l'arborescence d'un repertoire
     */
    public void printTreeCommand(){
            String mainDirectory = "/";
            //String file_or_directory_name = "";
            String file_type = "";
            String file_or_directory_name = "";
            stack = new Stack();
            visited = new HashSet();
            subFiles_or_subDirectories =  new HashSet();
            stack.push(mainDirectory);
            List<String> plainServerResponses = new ArrayList();
            
            String command = "PASV";
                     sendCommand(command);
                     opendataConnection();
                     command = "LIST";
                     sendCommand(command);
                     int index1 = 0;
                     
                    for (String plainServerResponse : dataresponses) {
                        
                            index1 = plainServerResponse.split(" ").length -1;
                            file_type = plainServerResponse.split(" ")[0];
                            file_or_directory_name = plainServerResponse.split(" ")[index1];
                           // System.out.println(plainServerResponse);
                            System.out.println(" |-"+file_or_directory_name);
                           
                     }
    }
    
    /**
     * methode qui permets de verifier l'element courant est un repertoire ou un fichier
     * @param fileType
     * @return 
     */
    
     public boolean isDirectory(String fileType){
         return  fileType.startsWith("dr");
     }
     
     /**
      * cette fonction permets d'afficher le nom du fichier en fonction du parametre level qui sert à l'indentation de l'affichage
      * @param fileName
      * @param level 
      */
     public void PrintName(String fileName, int level){
         
         for(int i = 0; i < level; i++){
             System.out.print(" ");
         }
         System.out.println(fileName);
     }
    
    /**
     * cette methode permets de fermer la connection de control avec le serveur
     */
   public void closeControlConnection(){
        try {
                reader.close();
                writer.close();
                controlSocket.close();
        } catch (IOException ex) {
                System.out.println("erreur lors de la fermeture de la connection");
            }
   }
   
   /**
    * cette fonction permets de fermer la connection de donnees avec le serveur
    */
   public void closeDtaConnection(){
       try {
            dataReader.close();
            dataWriter.close();
            dataSocket.close();
       } catch (Exception e) {
           System.out.println("impossible de fermer la connection de données!");
        }
   }
}

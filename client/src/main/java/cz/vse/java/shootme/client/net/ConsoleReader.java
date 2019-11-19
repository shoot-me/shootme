package cz.vse.java.shootme.client.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ConsoleReader extends Thread{

    private Socket socket;

    private Scanner scanner;

    private BufferedReader bufferedReader;

    private PrintStream printStream;


    ConsoleReader(Socket socket) throws IOException {
        this.socket = socket;
        scanner = new Scanner(System.in);
        printStream = new PrintStream(socket.getOutputStream(), true);
    }

    @Override
    public void run(){
        while (true) {
            String line = scanner.nextLine();
            printStream.println(line);

        }
    }



}

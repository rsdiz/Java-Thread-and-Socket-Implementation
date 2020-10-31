package client;

import java.io.*;
import java.net.Socket;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class Client
 * Implementasi dari Socket
 *
 * @author Muhammad Rosyid Izzulkhaq (5180411122)
 */
public class Client {
  /**
   * Fungsi main menjalankan Socket
   * Cara Pemakaian:
   * java Client [hhostname] [port]
   *
   * @param args Command line arguments
   */
  public static void main(String[] args) {
    String hostname = "";
    int port = 0;
    Scanner keyboard = new Scanner(System.in);

    switch (args.length) {
      case 0:
        do {
          try {
            System.out.print(
                    "Masukkan hostname server: ");
            hostname = keyboard.next();
            System.out.print(
                    "Masukkan port server: ");
            port = keyboard.nextInt();
          } catch (InputMismatchException exception) {
            System.out.println(
                    "Port atau host yang dimasukkan tidak valid!\n");
          }
          keyboard.nextLine();
        } while (port <= 0 || hostname.isBlank());
        break;
      case 1:
        do {
          try {
            hostname = args[0];
            System.out.print(
                    "Masukkan port server: ");
            port = keyboard.nextInt();
          } catch (InputMismatchException exception) {
            System.out.println(
                    "Port yang dimasukkan tidak valid!\n");
          }
          keyboard.nextLine();
        } while (port <= 0);
        break;
      case 2:
        hostname = args[0];
        port = Integer.parseInt(args[1]);
        break;
    }

    // Membuat koneksi ke server
    try (Socket connection = new Socket(hostname, port)) {

      OutputStream outputStream = connection.getOutputStream();
      PrintWriter writer =
              new PrintWriter(outputStream, true);

      String text;

      System.out.println("-- IP Lookup Program --");
      System.out.println("Ketik 'q' untuk keluar dari program.");

      do {
        System.out.print("Masukkan Alamat Domain: ");
        text = keyboard.next();
        if (text.equals("q")) continue;

        // Mengirim teks yang ditulis ke server
        writer.println(text);

        InputStream inputStream = connection.getInputStream();
        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(inputStream));

        // Menampilkan hasil yang dikirim dari server
        String response = reader.readLine();
        System.out.println(response);

      } while (!text.equals("q"));

    } catch (IOException e) {
      System.out.println(
              "Terjadi Masalah pada I/O: " + e.getMessage());
      e.printStackTrace();
    }

  }
}
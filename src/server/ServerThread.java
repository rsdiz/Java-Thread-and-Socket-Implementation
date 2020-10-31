package server;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Class ServerThread
 * Implementasi dari Thread
 *
 * @author Muhammad Rosyid Izzulkhaq (5180411122)
 */
public class ServerThread extends Thread {
    private final Socket socket;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            final InputStream inputStream = socket.getInputStream();
            final BufferedReader reader =
                    new BufferedReader(new InputStreamReader(inputStream));

            final OutputStream outputStream = socket.getOutputStream();
            final PrintWriter writer =
                    new PrintWriter(outputStream, true);

            String query;

            // Menampilkan IP address dari domain yang dimasukkan
            do {
                query = reader.readLine();
                if (query.equals("q")) continue;
                try {
                    InetAddress inetAddress =
                            InetAddress.getByName(query);
                    writer.println(
                            "IP Address dari "+query+": "
                                    +inetAddress.getHostAddress());
                } catch (IOException e) {
                    writer.println(
                            "Tidak dapat menemukan IP Address dari "
                                    + query);
                    writer.println(
                            "Penyebab: " + e.getLocalizedMessage());
                }
            } while (!query.equals("q"));

            socket.close();
        } catch (IOException e) {
            System.out.println(
                    "Terjadi Kesalahan pada  I/O : " + e.getMessage());
            e.getCause();
        }
    }
}

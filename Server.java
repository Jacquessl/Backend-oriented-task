import java.io.*;
import java.net.*;
import java.text.ParseException;


public class Server{

    public void Servering() throws IOException, ParseException {
        int port = 8081;
        ServerSocket ss = new ServerSocket(port);
        System.err.println("Server is runing on port: "+port);
        while(true){
            Socket cs = ss.accept();
            String data = "";
            while (true) {
                InputStream input = cs.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String line = reader.readLine();
                String[] request = line.split(" ");
                String url = request[1];
                Menu menu = new Menu();
                data = menu.get(url);
                break;
            }
            PrintWriter co = new PrintWriter(cs.getOutputStream(), false);
            co.write("HTTP/1.1 200 OK\r\n");
            co.write("\r\n");
            co.write(data);
            co.write("\r\n\r\n");
            co.flush();
            cs.shutdownOutput();
            co.close();
        }
    }
}

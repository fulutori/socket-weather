import java.io.*;
import java.net.*;

public class Server1 extends Thread {
	final static int PORT=49300;
	Player player;
	private Server1(Player player) {
		this.player = player;
	}
	public void run() {
		try {
			player.sendMessage("end\n");

			player.close();
		}catch( IOException e ) {
			System.err.println("----------");
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		// ソケットや入出力用のストリームの宣言
		ServerSocket echoServer = null;
		String line;
		BufferedReader is;
		PrintStream os;
		Socket clientSocket = null;

		try {
			echoServer = new ServerSocket(49300);
			while(true) {
				clientSocket = echoServer.accept();
				//Player p = new Player(clientSocket);
				is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				os = new PrintStream(clientSocket.getOutputStream());

				line = is.readLine();
				if (line==null) {
					continue;
				} else {
					System.out.println("Client: "+line);
				}
				//os.println(line);
				String command = "python weather_pyowm.py " + line;
				Process process = Runtime.getRuntime().exec(command);
				InputStream is2 = process.getInputStream();
				BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
				String line2 = br2.readLine();
				System.out.println(line2);
				os.println(line2);
			}
		}catch( IOException e ) {
			System.err.println("----------");
			e.printStackTrace();
		}
	}
}

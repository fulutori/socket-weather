import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) {
		//　ソケットや入出力用のストリームの宣言
		Socket echoSocket = null;
		DataOutputStream os = null;
		BufferedReader is = null;

		try {
			echoSocket = new Socket("192.168.37.129", 49300); //サーバーのIPアドレスとポート番号
			os = new DataOutputStream(echoSocket.getOutputStream());
			is = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: localhost");
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: localhost");
		}

		// サーバーにメッセージを送る
		if (echoSocket != null && os != null && is != null) {
			try {
				// メッセージを送信
				Scanner scanner = new Scanner(System.in); //文字入力
				System.out.print("都市名 >> "); //天気を知りたい都市名を入力
				String test = scanner.nextLine();
				test+="\n";
				os.write(test.getBytes());

				// サーバーからのメッセージを受け取り画面に表示
				String responseLine;
				if ((responseLine = is.readLine()) != null) {
					System.out.println(responseLine);
				}

				// 開いたソケットなどをクローズ
				os.close();
				is.close();
				echoSocket.close();
			} catch (UnknownHostException e) {
				System.err.println("Trying to connect to unknown host: " + e);
			} catch (IOException e) {
				System.err.println("IOException: " + e);
			}
		}
	}
}

import java.util.Scanner;
import java.io.*;
import java.net.*;
public class nhap2{
	public static void main(String[] args){
		try{
			System.out.print("Nhap dia chi IP cua server:");
			Scanner sc = new Scanner(System.in);
			String serverIP = sc.nextLine();

			System.out.print("Nhap cong udp cua server:");
			int udpPort = sc.nextInt();
			sc.nextLine();


			DatagramSocket ds = new DatagramSocket();
			InetAddress ia = InetAddress.getByName(serverIP);

			System.out.print("Nhap ho ten sinh vien:");
			String hotenSV = sc.nextLine();
			byte[] byteSV = hotenSV.getBytes();

			DatagramPacket dp = new DatagramPacket(byteSV,byteSV.length,ia,udpPort);

			ds.send(dp);
			System.out.print("goi da duoc gui di voi noi dung:"+hotenSV);

			//nhap cong tcp tu server

			byte[] bufferreceive = new byte[1024];
			DatagramPacket dpreceive = new DatagramPacket(bufferreceive,bufferreceive.length);

			ds.receive(dpreceive);

			String tcpPortString = new String(dpreceive.getData(),0,dpreceive.getLength());
			int tcpPortInt = Integer.parseInt(tcpPortString.trim());
			System.out.print("Da nhan cong tcp tu server:"+tcpPortString);
			ds.close();

			Socket s = new Socket(serverIP,tcpPortInt);
			System.out.print("Da ket noi voi TCP server:");

			System.out.print("Nhap ten file can lay tren Server: ");

			String tenfile = sc.nextLine();

			System.out.print("Nhap ten file can luu: ");

			String tenfileluu = sc.nextLine();

			// Noi ket den server

			// Lay ra 2 stream in-out

			InputStream is = s.getInputStream();

			OutputStream os = s.getOutputStream();

			PrintStream ps = new PrintStream(os);

			Scanner kb = new Scanner(is);

			DataInputStream dis = new DataInputStream(is);

			// Gui yeu cau (READ Tenfile)

			String yeucau = "READ " + tenfile;

			ps.println(yeucau);

			// Nhan dong dau tien la len (kich thuoc file)

			String str = kb.nextLine();

			int len = Integer.parseInt(str);

			// Neu len = 0 thi bao loi

			if(len==0)

				System.out.println("File rong hoac khong ton tai");

			else { // Khac 0 nhan tiep len byte

				int tong = 0;

				byte b[] = new byte[10000];

				FileOutputStream f = new FileOutputStream(tenfileluu);

				DataOutputStream dos = new DataOutputStream(f);

				while(true) {

					int n = dis.read(b);	// Nhan duoc n byte, luu vao b[]

					if(n > 0) {

						dos.write(b,0,n);	// Ghi vao file n byte

						tong += n;

						System.out.println("Da ghi den byte thu " + tong);

					}

					if(tong==len) break;	// Da het len byte

				}

				f.close();	// Dong file KQ

				System.out.println("Da ghi file thanh cong");

			}

			// Dong noi ket

			s.close();

		}

		catch(UnknownHostException e) {

			System.out.println("Sai dia chi");

		}

		catch(FileNotFoundException e) {

			System.out.println("Khong tim thay file");

		}

		catch(IOException e) {

			System.out.println("Loi nhap xuat");

		}

	}

}
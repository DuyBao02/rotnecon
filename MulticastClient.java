// Ho va ten sinh vien : Nguyen Tuan Anh
// Mssv : B2016945

import java.io.*;
import java.net.*;

public class MulticastClient {
    private static final String GROUP_ADDRESS = "231.2.3.4";
    private static final int PORT = 23456;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        try {
            // Tạo MulticastSocket và tham gia nhóm
            MulticastSocket socket = new MulticastSocket(PORT);
            InetAddress group = InetAddress.getByName(GROUP_ADDRESS);
            socket.joinGroup(group);

            System.out.println("Client đã sẵn sàng nhận file...");

            int fileNumber = 1;
            while (true) {
                // Tạo ByteArrayOutputStream để lưu nội dung file
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                while (true) {
                    byte[] buffer = new byte[BUFFER_SIZE];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);

                    int receivedLength = packet.getLength();
                    
                    if (receivedLength == 0) {
                        // Gặp gói kết thúc
                        break;
                    }

                    // Ghi dữ liệu nhận được vào baos
                    baos.write(buffer, 0, receivedLength);
                    System.out.println("Đã nhận " + receivedLength + " bytes");
                }

                // Lưu dữ liệu thành file
                byte[] fileContent = baos.toByteArray();
                String outputFileName = "received_file_" + fileNumber + ".dat";
                FileOutputStream fos = new FileOutputStream(outputFileName);
                fos.write(fileContent);
                fos.close();

                System.out.println("Đã lưu file: " + outputFileName);
                fileNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
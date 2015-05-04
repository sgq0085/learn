package com.gqshao.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPClient {
    public static void main(String[] args) throws Exception {
        // 发送端
        try {
            // 创建发送方的套接字 对象 不传入端口默认分配随机端口
            DatagramSocket socket = new DatagramSocket();
            // 设置超时时间 200ms
            socket.setSoTimeout(200);
            // 发送的内容
            String text = "61.28.30.125";
            byte[] buf = text.getBytes();
            // 构造数据报包，用来将长度为 length 的包发送到指定主机上的指定端口号。
            DatagramPacket sendPacket = new DatagramPacket(buf, buf.length, InetAddress.getByName("192.168.144.60"), 10111);
            // 从此套接字发送数据报包
            socket.send(sendPacket);
            // 接收，接收者返回的数据
            byte[] buffer = new byte[10000];
            DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(receivePacket);
            byte data[] = receivePacket.getData();
            // 接收的数据解析
            InetAddress address = receivePacket.getAddress();// 接收的地址
            System.out.println("接收的ip地址 : " + address.toString());
            System.out.println("接收的端口 : " + receivePacket.getPort());
            String res = new String(data);
            System.out.println("接收的文本 : 长度" + res.length() + ", 内容 " + res);
            String bufferStr = new String(buffer);
            System.out.println("接收的文本的byte[]的长度 : " + bufferStr.length() + ", 内容 " + bufferStr);
            System.out.println("两者是否是同一对象 : " + (res == bufferStr));
            System.out.println("两者内容是否相同 : " + res.equals(bufferStr));
            // 关闭此数据报套接字。
            socket.close();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

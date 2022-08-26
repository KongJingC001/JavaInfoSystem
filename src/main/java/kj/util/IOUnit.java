package kj.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Files;

public class IOUnit {

	/**
	 * 系统库本地文件所在地
	 */
	private static final File file = new File("./info.dat");

	/**
	 * 服务器系统库文件
	 */
	private static final File serviceFile = new File("./system.dat");

	/**
	 * socket实例
	 */
	public static Socket socket = null;

	private synchronized static void save(Object obj, File file) {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try (OutputStream outputStream = Files.newOutputStream(file.toPath());
			 ObjectOutputStream objStream = new ObjectOutputStream(outputStream)) {
			objStream.writeObject(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 从服务器读取数据
		if (socket != null && socket.isConnected()) {
			try {
				OutputStream outputStream = socket.getOutputStream();
				ObjectOutputStream objStream = new ObjectOutputStream(outputStream);
				objStream.writeObject(obj);
			} catch (SocketException e) {
				System.out.println("连接关闭");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("保存完毕");
	}

	/**
	 * 保存对象到系统库文件中
	 * 
	 */
	public synchronized static void save(Object obj) {
		// 如果服务器文件存在，即用户在服务器上运行该程序，优先保存到服务器
		if (serviceFile.exists()) {
			save(obj, file);
		}
		save(obj, file);
	}

	/**
	 * 从系统库文件中读取对象
	 * 
	 */
	private synchronized static Object load(File file) {
		if (!file.exists()) {
			return null;
		}
		Object obj = null;
		try (InputStream inputStream = Files.newInputStream(file.toPath());
			 ObjectInputStream objStream = new ObjectInputStream(inputStream)) {
			obj = objStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("数据读取完毕");
		return obj;
	}

	public synchronized static Object load() {
		// 如果服务器文件存在，即用户在服务器上运行该程序，优先读取服务器数据
		if (serviceFile.exists()) {
			return load(serviceFile);
		}
		return load(file);
	}

}

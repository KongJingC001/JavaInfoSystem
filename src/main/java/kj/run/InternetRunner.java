package kj.run;

import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class InternetRunner {

	/**
	 * 系统库文件
	 */
	private static final File file = new File("./system.dat");

	private static int port = -1;

	private JTextArea info;

	public static void main(String[] args) {
		String p;
		do {
			p = JOptionPane.showInputDialog(null, "请输入可用的端口号", "端口号", JOptionPane.INFORMATION_MESSAGE);
			if (p == null) {
				System.exit(0);
			}
			if (!isLegalPort(p)) {
				JOptionPane.showMessageDialog(null, "输入了一个非法端口号", "非法端口号", JOptionPane.ERROR_MESSAGE);
				continue;
			}
			break;
		} while (true);
		port = Integer.parseInt(p);
		// 启动GUI程序
		SwingUtilities.invokeLater(() -> new InternetRunner().go());

	}

	// 启动GUI
	private void go() {
		JFrame f = new JFrame("服务器");
		JPanel panel = new JPanel(new BorderLayout());
		info = new JTextArea("服务器开始运行");
		JScrollPane scroll = new JScrollPane(info);
		panel.add(scroll);
		f.getContentPane().add(panel);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(400, 350);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
		openService();
	}

	// 开启服务
	private void openService() {
		// 开启服务器，接收客户端输入
		SwingWorker<Object, Boolean> task = new SwingWorker<Object, Boolean>() {

			@Override
			protected Object doInBackground() {
				try (ServerSocket server = new ServerSocket(port)) {
					while (true) {
						try {
							Socket client = server.accept();
							// System.out.println("获取到连接：" + client.getLocalAddress());
							setInfo("获取到连接：" + client.getInetAddress());
							Thread thread = new Thread(new ClientHandler(client));
							thread.start();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				return null;
			}
		};
		task.execute();
	}

	/**
	 * 设置信息
	 * 
	 */
	private void setInfo(String str) {
		// info.setText(info.getText() + "\n" + str);
		info.append("\n" + str);
	}

	/**
	 * 判断是否为合法端口号
	 * 
	 */
	public static boolean isLegalPort(String port) {
		if (port == null || port.trim().isEmpty())
			return false;
		for (int i = 0; i < port.length(); i++) {
			// 数字合法性判断，首位数字不能是零，除非这个数字是零
			if (port.length() != 1 && port.charAt(0) == '0')
				return false;
			// 这是一个数字
			if (port.charAt(i) < '0' || port.charAt(i) > '9')
				return false;
		}
		int num = Integer.parseInt(port);
		// 检验每一段长度
		return num <= 65535;
	}

	/**
	 * 处理用户响应
	 */
	public class ClientHandler implements Runnable {

		Socket socket;

		public ClientHandler(Socket socket) {
			this.socket = socket;
		}

		/**
		 * 当用户开始
		 */
		public void run() {
			receive();
			setInfo(socket.getInetAddress() + "连接结束");
		}

		private void receive() {
			ArrayList<Integer> res = new ArrayList<>();
			setInfo("开始接收客户端数据");
			// 获取客户端发来的数据，一直等待用户的输入
			InputStream inStream = null;
			try {
				inStream = socket.getInputStream();
				int c;
				while ((c = inStream.read()) != -1) {
					res.add(c);
				}
			} catch (SocketException e) {
				setInfo("接收数据时连接关闭");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					inStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			setInfo("接收信息结束，开始将数据写入到本地服务器中");
			// 写入服务器本地文件中
			try (OutputStream outStream = Files.newOutputStream(file.toPath())) {
				for (Integer integer : res) {
					outStream.write(integer);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			setInfo("写入到本地服务器成功！");
		}

	}
}

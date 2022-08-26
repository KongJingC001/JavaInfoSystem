package kj.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import kj.util.IOUnit;

/**
 * @description: 菜单界面
 * @time: 2020-04-13 14:06:46
 * @author KONG
 */
public class MenuInterface extends JMenuBar {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3182925796712253530L;

	/**
	 * 一级菜单
	 */
	private JMenu service;

	/**
	 * 服务器子菜单
	 */
	private JMenuItem connect, close;

	/**
	 * 构造方法
	 */
	public MenuInterface() {
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		service = new JMenu("服务器");
		connect = new JMenuItem("连接到");
		close = new JMenuItem("断开连接");
		setAttribute();
		addNode();
	}

	/**
	 * 设置菜单属性
	 */
	private void setAttribute() {
		connect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("点击连接到服务器按钮");
				try {
					connect();
				} catch (InterruptedException | ExecutionException e1) {
					JOptionPane.showMessageDialog(null, "连接出现异常，请在目标IP地址上运行服务器且打开端口", "连接失败",
							JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		close.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("点击关闭与服务器连接按钮");
				close();
			}
		});
	}

	/**
	 * 添加组件
	 */
	private void addNode() {
		service.add(connect);
		service.addSeparator();
		service.add(close);
		this.add(service);
	}

	/**
	 * 连接到服务器
	 * 
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	private synchronized void connect() throws InterruptedException, ExecutionException {
		String ip = JOptionPane.showInputDialog(null, "请输入服务器的IP地址", "连接到", JOptionPane.INFORMATION_MESSAGE);
		// 检验IP地址合法性
		if (!isLegalIP(ip)) {
			JOptionPane.showMessageDialog(null, "输入了一个非法IP地址", "非法IP", JOptionPane.ERROR_MESSAGE);
		} else {
			String port = JOptionPane.showInputDialog(null, "请输入端口号", "端口", JOptionPane.INFORMATION_MESSAGE);
			if (!isLegalPort(port)) {
				JOptionPane.showMessageDialog(null, "输入了一个非法端口号", "非法端口号", JOptionPane.ERROR_MESSAGE);
			} else {
				// 建立连接
				SwingWorker<Socket, Boolean> task = new SwingWorker<Socket, Boolean>() {

					@Override
					protected Socket doInBackground() throws Exception {
						Socket s = new Socket(ip, Integer.valueOf(port));
						if (s.isConnected())
							return s;
						return null;
					}
				};
				task.execute();
				IOUnit.socket = task.get();
				// refreshTable();
			}
		}

	}

	/**
	 * 关闭连接
	 */
	private void close() {
		// 没有建立连接或连接已被关闭
		if (IOUnit.socket == null) {
			JOptionPane.showMessageDialog(null, "没有建立任何连接！", "关闭出错", JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				IOUnit.socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			IOUnit.socket = null;
			JOptionPane.showMessageDialog(null, "连接已关闭！", "关闭连接", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	/**
	 * 判断是否为合法端口号
	 * 
	 * @param port
	 * @return
	 */
	private boolean isLegalPort(String port) {
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
		int num = Integer.valueOf(port);
		// 检验每一段长度
		if (num > 65535)
			return false;
		return true;
	}

	/**
	 * 判断是否为合法IP
	 * 
	 * @param ip
	 * @return
	 */
	private boolean isLegalIP(String ip) {
		if (ip == null || ip.trim().isEmpty())
			return false;
		int point = 0;
		for (int i = 0; i < ip.length(); i++) {
			if ('.' == ip.charAt(i)) {
				point++;
			}
		}
		// 有且仅有三个点
		if (point != 3)
			return false;
		// 校验每个IP号的合法性
		String[] ipNum = ip.split(".");
		for (String str : ipNum) {
			for (int i = 0; i < str.length(); i++) {
				// 数字合法性判断，首位数字不能是零，除非这个数字是零
				if (str.length() != 1 && str.charAt(0) == '0')
					return false;
				// 这是一个数字
				if (str.charAt(i) < '0' || str.charAt(i) > '9')
					return false;
			}
			int num = Integer.valueOf(str);
			// 检验每一段长度
			if (num > 255)
				return false;
		}
		return true;
	}
}

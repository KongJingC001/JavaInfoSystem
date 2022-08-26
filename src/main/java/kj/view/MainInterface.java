package kj.view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import kj.util.Picture;

/**
 * @description: 主面板
 * @time: 2020-04-07 13:34:54
 * @author KONG
 */
public class MainInterface extends JFrame {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 8270143659679309387L;

	/**
	 * 菜单栏
	 */
	private MenuInterface menuInterface;

	/**
	 * 管理面板
	 */
	private ManageInterface manageInterface;

	/**
	 * 构造方法
	 */
	public MainInterface(MenuInterface menuInterface, ManageInterface manageInterface) {
		this.menuInterface = menuInterface;
		this.manageInterface = manageInterface;
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		this.setIconImage(Picture.getInstance().getImageIcon("icon.png").getImage());
		this.setTitle("学生信息管理系统");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setSize(1200, 800);
		this.setLocationRelativeTo(null);
		addNode();
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// 检测用户是否更改所有数据
				if (manageInterface.isSaved()) {
					exit();
				} else {
					int option = JOptionPane.showConfirmDialog(MainInterface.this, "修改尚未保存，是否保存后退出？\n直接退出会丢失你更改的数据！",
							"您正在执行退出操作", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
					switch (option) {
					case JOptionPane.YES_OPTION:
						manageInterface.save();
						exit();
						break;
					case JOptionPane.NO_OPTION:
						exit();
						break;
					case JOptionPane.CANCEL_OPTION:
					case JOptionPane.CLOSED_OPTION:
						break;
					}
				}

			}

		});
	}

	/**
	 * 添加节点
	 */
	private void addNode() {
		this.setJMenuBar(menuInterface);
		this.getContentPane().add(manageInterface);
	}

	/**
	 * 启动方法
	 */
	public void start() {
		this.setVisible(true);
	}

	/**
	 * 结束程序
	 */
	private void exit() {
		System.exit(0);
	}
}

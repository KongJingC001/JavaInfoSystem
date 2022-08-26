package kj.run;

import javax.swing.SwingUtilities;

import kj.controller.MainController;
import kj.model.TableData;
import kj.view.MainInterface;
import kj.view.ManageInterface;
import kj.view.MenuInterface;
import kj.view.StudentTable;

/**
 * description: 管理系统启动方法
 * time: 2020-04-07 13:30:09
 * @author KONG
 */
public class SystemV3Runner {

	public static void main(String[] args) {
		System.out.println("程序启动！");
		SwingUtilities.invokeLater(SystemV3Runner::startGUI);
	}

	/**
	 * 启动GUI
	 */
	public static void startGUI() {
		// 菜单栏
		MenuInterface menuInterface = new MenuInterface();
		// 数据模型
		TableData tableData = new TableData();
		// 学生数据表
		StudentTable table = new StudentTable(tableData);
		// 信息管理面板
		ManageInterface manageInterface = new ManageInterface(table);
		// 主界面
		MainInterface mainInterface = new MainInterface(menuInterface, manageInterface);
		// 主控制器
		MainController mainController = new MainController(mainInterface);
		// 主界面
		mainController.start();
	}

}

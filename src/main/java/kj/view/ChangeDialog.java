package kj.view;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import kj.style.StyleBox;

/**
 * description: 更改信息对话框
 * @author KONG
 */
public class ChangeDialog {

	/**
	 * 对话框对象
	 */
	private final JDialog dialog;

	/**
	 * 承载面板
	 */
	private JPanel panel;

	/**
	 * 提示
	 */
	private final JLabel hintLabel;

	/**
	 * ID标签
	 */
	private final JLabel idLabel;

	/**
	 * 输入框
	 */
	private final JTextField idInput;

	/**
	 * 更改按钮
	 */
	private final JButton changeButton;

	/**
	 * 展示结果
	 */
	private final JLabel resultLabel;

	/**
	 * 所有数据
	 */
	private final StudentTable data;

	/**
	 * 构造方法
	 * 
	 */
	public ChangeDialog(StudentTable data) {
		this.data = data;
		dialog = new JDialog();
		hintLabel = new JLabel("输入一个ID试图改变信息！");
		idLabel = new JLabel("ID:");
		idInput = new JTextField(10);
		resultLabel = new JLabel("没有执行任何操作");
		changeButton = new JButton("更改信息");
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		dialog.setModal(true);
		dialog.setTitle("更改信息");
		dialog.setResizable(false);
		dialog.setSize(240, 150);
		dialog.setLocationRelativeTo(null);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setAttribute();
		addNode();
	}

	/**
	 * 显示对话框
	 */
	public void showDialog() {
		dialog.setVisible(true);
	}

	/**
	 * 设置组件属性
	 */
	private void setAttribute() {
		hintLabel.setFont(StyleBox.DIALOG_HINT);
		changeButton.setBackground(StyleBox.BUTTON_BACKGROUND_COLOR);
		// 关闭actionPerformed()方法
		changeButton.addActionListener(e -> {
			System.out.println("用户点击了更改按钮");
			// 检索用户输入的ID，找到对应的学生信息
			String id = idInput.getText();
			// 用户输入空的值
			if (id == null || id.trim().isEmpty()) {
				JOptionPane.showMessageDialog(dialog, "不要输入空的ID", "空值", JOptionPane.ERROR_MESSAGE);
			} else {
				// 得到标准id
				id = data.getStandardId(id);
				// 得到学生串，实际最多只有一个值，因为一个ID对应唯一一个学生信息
				String stu = data.findINFOById(id).toString();
				stu = stu.replace("[", "");
				stu = stu.replace("]", "");
				stu = "学生信息：" + stu;
				System.out.println(stu);
				// 用户选择要更改的信息
				Object tar = JOptionPane.showInputDialog(dialog, stu, "选择你要更改的项目", JOptionPane.INFORMATION_MESSAGE,
						null, new String[] { "姓名", "年龄", "性别", "加入时间" }, null);
				// 进行下一步
				if (tar == null) {
					// 用户没有输入值
				} else {
					String res = null;
					switch ((String) tar) {
					case "姓名":
						// 用户输入新的信息
						res = JOptionPane.showInputDialog(dialog, "更改用户" + id + "的姓名", "输入一个名字",
								JOptionPane.INFORMATION_MESSAGE);
						break;
					case "年龄":
						// 用户输入新的信息
						Object _age = JOptionPane.showInputDialog(dialog, "更改用户" + id + "的年龄", "选择一个年龄",
								JOptionPane.INFORMATION_MESSAGE, null, DateChooser.getNumbers(10, 120), null);
						res = String.valueOf(_age).equals("null") ? null : String.valueOf(_age);
						break;
					case "性别":
						// 用户输入新的信息
						res = (String) JOptionPane.showInputDialog(dialog, "更改用户" + id + "的性别", "选择你的性别",
								JOptionPane.INFORMATION_MESSAGE, null, new String[] { "男", "女" }, null);
						break;
					case "加入时间":
						DateChooser chooser = new DateChooser();
						res = chooser.getDate();
						break;
					}
					// 处理用户输入
					if (res == null) {
						// 用户没有输入值
					} else {
						// 进行更改
						System.out.println(res);
						// 进行更改
						switch ((String) tar) {
						case "姓名":
							data.setNameById(res, id);
							break;
						case "年龄":
							data.setAgeById(Integer.parseInt(res), id);
							break;
						case "性别":
							data.setSexById(res, id);
							break;
						case "加入时间":
							data.setJoinTimeById(res, id);
							break;
						}
					}
				}
			}
		}); // 关闭匿名内部类
	} // 关闭setAttribute()方法

	/**
	 * 添加组件
	 */
	private void addNode() {
		panel = new JPanel();
		panel.add(hintLabel);
		panel.add(idLabel);
		panel.add(idInput);
		panel.add(changeButton);
		panel.add(resultLabel);
		dialog.setContentPane(panel);
	}

}

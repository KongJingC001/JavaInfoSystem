package kj.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import kj.style.StyleBox;

/**
 * @description: 删除信息对话框
 * @time: 2020-04-11 13:03:28
 * @author KONG
 */
public class DeleteDialog {

	/**
	 * 删除所有数据符号
	 */
	private static final String DELETE_ALL_CH = "*";

	/**
	 * 结果显示最大长度
	 */
	private static final int RESULT_MAX_LENGTH = 24;

	/**
	 * 对话框对象
	 */
	private JDialog dialog;

	/**
	 * 承载面板
	 */
	private JPanel panel;

	/**
	 * 提示
	 */
	private JLabel hintLabel;

	/**
	 * ID标签
	 */
	private JLabel idLabel;

	/**
	 * 输入框
	 */
	private JTextField idInput;

	/**
	 * 删除按钮
	 */
	private JButton deleteButton;

	/**
	 * 展示结果
	 */
	private JLabel resultLabel;

	/**
	 * 所有数据
	 */
	private StudentTable datas;

	/**
	 * 构造方法
	 */
	public DeleteDialog(StudentTable datas) {
		this.datas = datas;
		dialog = new JDialog();
		hintLabel = new JLabel("输入一个ID或<" + DELETE_ALL_CH + ">(删除所有信息)！");
		idLabel = new JLabel("ID:");
		idInput = new JTextField(10);
		resultLabel = new JLabel("没有执行任何操作");
		deleteButton = new JButton("删除");
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		dialog.setModal(true);
		dialog.setTitle("删除信息");
		dialog.setResizable(false);
		dialog.setSize(300, 150);
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
		deleteButton.setBackground(StyleBox.BUTTON_BACKGROUND_COLOR);
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("用户点击了删除按钮");
				// 检索用户信息
				String _id = idInput.getText();
				// 用户是否要删除所有数据
				if (DELETE_ALL_CH.equals(_id)) {
					int option = JOptionPane.showConfirmDialog(dialog, "是否要删除所有数据？", "危险操作", JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE);
					switch (option) {
					case JOptionPane.YES_OPTION:
						datas.deleteAllINFO();
						setResultLabel("所有数据被删除！");
						break;
					case JOptionPane.NO_OPTION:
					case JOptionPane.CLOSED_OPTION:
						System.out.println("用户取消了删除所有数据操作");
						break;
					} // 关闭switch-case块
				} else {
					// 是否为合法ID
					if (datas.isLegalId(_id)) {
						// 转换为标准ID
						_id = datas.getStandardId(_id);
						if (datas.hasThisID(_id)) {
							System.out.print("存在学生信息：");
							// 删除用户信息
							String tar = datas.deleteINFOById(_id);
							setResultLabel(tar);
							System.out.println("已删除");
						} else {
							System.out.println("不存在该学生");
							setResultLabel("不存在该学生ID！");
						} // 关闭内层if-else
					} // 关闭if
				} // 关闭外层if-else
			}// 关闭actionPerformed()方法

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
		panel.add(deleteButton);
		panel.add(resultLabel);
		dialog.setContentPane(panel);
	}

	/**
	 * 设置显示结果
	 * 
	 * @param tar
	 */
	private void setResultLabel(String tar) {
		tar = tar.replace("[", "");
		tar = tar.replace("]", "");
		if (tar.length() > RESULT_MAX_LENGTH) {
			tar = tar.substring(0, RESULT_MAX_LENGTH);
		}
		resultLabel.setText("操作结果：" + tar);
	}

}

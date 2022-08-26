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
 * @description: 记录信息对话框
 * @time: 2020-04-13 13:13:56
 * @author KONG
 */
public class INFODialog {
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
	 * 提交按钮
	 */
	private JButton registerButton;

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
	 * 
	 * @param datas
	 */
	public INFODialog(StudentTable datas) {
		this.datas = datas;
		dialog = new JDialog();
		hintLabel = new JLabel("输入一个ID来登记信息！");
		idLabel = new JLabel("ID:");
		idInput = new JTextField(10);
		resultLabel = new JLabel("没有执行任何操作");
		registerButton = new JButton("登记");
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		dialog.setModal(true);
		dialog.setTitle("登记健康信息");
		dialog.setResizable(false);
		dialog.setSize(220, 130);
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
		registerButton.setBackground(StyleBox.BUTTON_BACKGROUND_COLOR);
		registerButton.addActionListener(new ActionListener() {

			final String[] temperature = { "低于36.5", "36.5", "36.6", "36.7", "36.8", "36.9", "37.0", "37.1", "37.2",
					"37.3", "37.4", "37.5", "过热异常" };

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("用户点击了登记按钮");
				String id = idInput.getText();
				id = datas.getStandardId(id);
				// 用户输入空的值
				if (id == null || id.trim().isEmpty()) {
					JOptionPane.showMessageDialog(dialog, "不要输入空的ID", "空值", JOptionPane.ERROR_MESSAGE);
				} else {
					// 记录体温信息
					String temp = (String) JOptionPane.showInputDialog(dialog, "请选择您当前测量的额头实际体温", "记录体温",
							JOptionPane.INFORMATION_MESSAGE, null, temperature, null);
					// 用户没有选择记录
					if (temp == null) {
						JOptionPane.showMessageDialog(dialog, "放弃记录体温！", "操作失败", JOptionPane.INFORMATION_MESSAGE);
					} else {
						// 设置体温信息
						datas.setBodyTEMPById(temp, id);
						// 用户记录了体温，进一步录入地址信息
						int option = JOptionPane.showConfirmDialog(dialog, "居住地址是否发生更改？", "询问",
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						// 判断用户是否需要更改地址
						switch (option) {
						case JOptionPane.YES_OPTION:
							String address = JOptionPane.showInputDialog(dialog, "请输入当前居住地址", "记录地址",
									JOptionPane.INFORMATION_MESSAGE);
							if (address == null || address.trim().isEmpty()) {
								JOptionPane.showMessageDialog(dialog, "没有输入或输入一个空的地址！", "操作失败",
										JOptionPane.ERROR_MESSAGE);
							} else {
								// 用户成功记录地址信息
								datas.setAddressById(address, id);
							}
							break;
						case JOptionPane.NO_OPTION:
						case JOptionPane.CLOSED_OPTION:
							break;
						} // 关闭switch-case块
					} // 关闭内部if-else
				} // 关闭外部if-else
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
		panel.add(registerButton);
		panel.add(resultLabel);
		dialog.setContentPane(panel);
	}

}

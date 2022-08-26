package kj.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import kj.model.Student;
import kj.style.LayoutBox;
import kj.style.StyleBox;

/**
 * description: 添加信息对话框
 * @author KONG
 */
public class AddDialog {

	private final String EMPTY_DATE = "****-**-**";

	/**
	 * 对话框
	 */
	private JDialog dialog;

	/**
	 * 对话框控件
	 */
	private JLabel idLabel;
	private JTextField id;
	/**
	 * 生成ID号
	 */
	private JButton diceButton;
	private JLabel nameLabel;
	private JTextField name;
	private JLabel ageLabel;
	private JComboBox<Integer> age;
	private JPanel sexPanel;
	private JRadioButton manSex;
	private JRadioButton womenSex;
	private JLabel dateLabel;
	private JLabel date;
	/**
	 * 打开日期选取对话框
	 */
	private JButton open;

	/**
	 * 确定取消按钮
	 */
	private JButton confirm, cancel;

	/**
	 * 数据表
	 */
	private final StudentTable data;

	/**
	 * 构造方法
	 */
	public AddDialog(StudentTable data) {
		this.data = data;
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		initInstance();
		dialog.setModal(true);
		dialog.setTitle("添加信息");
		dialog.setResizable(false);
		dialog.setSize(LayoutBox.DIALOG_WIDTH, LayoutBox.DIALOG_HEIGHT);
		dialog.setLocationRelativeTo(null);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setAttribute();
		setLayout();
		addNode();
	}

	public void showDialog() {
		dialog.setVisible(true);
	}

	/**
	 * 初始化实例
	 */
	private void initInstance() {
		dialog = new JDialog();
		idLabel = new JLabel("ID:");
		id = new JTextField();
		diceButton = new JButton("生成一个ID");
		nameLabel = new JLabel("姓名：");
		name = new JTextField();
		ageLabel = new JLabel("年龄：");
		age = new JComboBox<Integer>(DateChooser.getNumbers(16, 60));
		sexPanel = new JPanel();
		manSex = new JRadioButton("男");
		womenSex = new JRadioButton("女");
		dateLabel = new JLabel("加入时间：");
		date = new JLabel(EMPTY_DATE);
		open = new JButton("选择一个日期");
		confirm = new JButton("确认");
		cancel = new JButton("取消");
	}

	/**
	 * 设置组件属性
	 */
	private void setAttribute() {
		idLabel.setFont(StyleBox.DIALOG_FONT);
		id.setFont(idLabel.getFont());
		nameLabel.setFont(idLabel.getFont());
		name.setFont(id.getFont());
		ageLabel.setFont(idLabel.getFont());
		age.setFont(id.getFont());
		dateLabel.setFont(idLabel.getFont());
		date.setFont(dateLabel.getFont());
		// 组装单选按钮
		sexPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "性别",
				TitledBorder.LEFT, TitledBorder.TOP, StyleBox.DIALOG_FONT));
		ButtonGroup sex = new ButtonGroup();
		sex.add(manSex);
		sex.add(womenSex);
		diceButton.setBackground(StyleBox.BUTTON_BACKGROUND_COLOR);
		open.setBackground(StyleBox.BUTTON_BACKGROUND_COLOR);
		confirm.setBackground(StyleBox.BUTTON_BACKGROUND_COLOR);
		cancel.setBackground(StyleBox.BUTTON_BACKGROUND_COLOR);
		addListener();
	}

	/**
	 * 给组件添加事件
	 */
	private void addListener() {
		diceButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.print("系统生成ID号：");
				String _id = data.getAutoID();
				if (_id == null) {
					JOptionPane.showMessageDialog(dialog, "数据库已满，无法继续添加数据！", "生成失败", JOptionPane.WARNING_MESSAGE);
				} else {
					id.setText(_id);
				}
			}
		});
		// 给“日期”对话框按钮添加事件
		open.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DateChooser chooser = new DateChooser();
				// 获得用户的选择
				String t = chooser.getDate();
				if (t == null) {
					// 用户输入错误的日期，没有任何改变
					JOptionPane.showMessageDialog(dialog, "输入非法日期，请重新选择！", "无效操作", JOptionPane.ERROR_MESSAGE);
				} else {
					date.setText(chooser.getDate());
				}
			}
		});
		// 给确定按钮添加事件
		confirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("用户点击确认按钮");
				String _id = id.getText();
				// 补足ID位数为8位
				while (_id.length() < Student.getID_LENGTH()) {
					_id = "0" + _id;
				}
				String _name = name.getText();
				int _age = (Integer) age.getSelectedItem();
				boolean _sex = manSex.isSelected() ? true : false;
				String _time = date.getText();
				// 判断信息是否合法
				if (isLegalINFO(_id, _name, _time)) {
					// 寄存该信息
					Student newStudent = new Student(_id, _name, _age, _sex, _time);
					System.out.println("添加数据：" + newStudent);
					data.addINFO(newStudent);
					dialog.dispose();
				}
			}

		});
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("用户取消了输入");
				dialog.dispose();
			}
		});
		dialog.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(dialog, "取消本次输入？", "确认", JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.WARNING_MESSAGE);
				// 处理用户行为
				switch (result) {
				// 用户点击确定时关闭对话框
				case JOptionPane.YES_OPTION:
					dialog.dispose();
					break;
				case JOptionPane.NO_OPTION:
					break;
				case JOptionPane.CLOSED_OPTION:
					break;
				}
			}
		});
	}

	/**
	 * 设置绝对布局
	 */
	private void setLayout() {
		idLabel.setBounds(LayoutBox.MARGIN_LEFT, LayoutBox.ID_Y, LayoutBox.LABEL_WIDTH, LayoutBox.LABEL_HEIGHT);
		id.setBounds(LayoutBox.TEXTFIELD_X, LayoutBox.ID_Y, LayoutBox.ID_TEXTFIELD_WIDTH, LayoutBox.TEXTFIELD_HEIGHT);
		diceButton.setBounds(LayoutBox.DICE_BUTTON_X, LayoutBox.ID_Y, LayoutBox.DICE_BUTTON_WIDTH,
				LayoutBox.BUTTON_HEIGHT);
		nameLabel.setBounds(LayoutBox.MARGIN_LEFT, LayoutBox.NAME_Y, LayoutBox.LABEL_WIDTH, LayoutBox.LABEL_HEIGHT);
		name.setBounds(LayoutBox.TEXTFIELD_X, LayoutBox.NAME_Y, LayoutBox.NAME_TEXTFIELD_WIDTH,
				LayoutBox.TEXTFIELD_HEIGHT);
		ageLabel.setBounds(LayoutBox.MARGIN_LEFT, LayoutBox.AGE_Y, LayoutBox.LABEL_WIDTH, LayoutBox.LABEL_HEIGHT);
		age.setBounds(LayoutBox.AGE_X, LayoutBox.AGE_Y, LayoutBox.AGE_WIDTH, LayoutBox.AGE_HEIGHT);
		sexPanel.setBounds(LayoutBox.SEX_X, LayoutBox.SEX_Y, LayoutBox.SEX_WIDTH, LayoutBox.SEX_HEIGHT);
		dateLabel.setBounds(LayoutBox.DATE_LABEL_X, LayoutBox.DATE_Y, LayoutBox.DATE_BUTTON_WIDTH,
				LayoutBox.DATE_BUTTON_HEIGHT);
		date.setBounds(LayoutBox.DATE_TIME_X, LayoutBox.DATE_Y, LayoutBox.DATE_BUTTON_WIDTH,
				LayoutBox.DATE_BUTTON_HEIGHT);
		open.setBounds(LayoutBox.DATE_BUTTON_X, LayoutBox.DATE_Y, LayoutBox.DATE_BUTTON_WIDTH,
				LayoutBox.DATE_BUTTON_HEIGHT);

		confirm.setBounds(LayoutBox.CONFIRM_X, LayoutBox.BUTTON_Y, LayoutBox.BUTTON_WIDTH, LayoutBox.BUTTON_HEIGHT);
		cancel.setBounds(LayoutBox.CANCEL_X, LayoutBox.BUTTON_Y, LayoutBox.BUTTON_WIDTH, LayoutBox.BUTTON_HEIGHT);
	}

	/**
	 * 添加节点
	 */
	private void addNode() {
		JPanel panel = new JPanel(null);
		panel.add(idLabel);
		panel.add(id);
		panel.add(diceButton);
		panel.add(nameLabel);
		panel.add(name);
		panel.add(ageLabel);
		panel.add(age);
		sexPanel.add(manSex);
		sexPanel.add(womenSex);
		panel.add(sexPanel);
		panel.add(dateLabel);
		panel.add(date);
		panel.add(open);
		panel.add(confirm);
		panel.add(cancel);
		dialog.setContentPane(panel);
	}

	/**
	 * 判断信息是否合法
	 * 
	 */
	private boolean isLegalINFO(String id, String name, String time) {
		// 检测用户ID是否输入过长
		if (data.isTooLonger(id)) {
			return false;
		}
		// 先判断用户是否录入了所有信息
		if (id == null || id.trim().isEmpty() || name == null || name.trim().isEmpty()
				|| (!manSex.isSelected() && !womenSex.isSelected()) || time.equals(EMPTY_DATE)) {
			String message = null;
			if (id == null || id.trim().isEmpty()) {
				message = "请输入一个ID！";
			} else if (name == null || name.trim().isEmpty()) {
				message = "请输入一个名字！";
			} else if (!manSex.isSelected() && !womenSex.isSelected()) {
				message = "请选择一个性别";
			} else if (time.equals(EMPTY_DATE)) {
				message = "请选择日期";
			}
			// 提示用户录入完整信息
			JOptionPane.showMessageDialog(dialog, message, "录入不完整", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		// 检测是否有重复ID
		if (data.hasThisID(id)) {
			JOptionPane.showMessageDialog(dialog, "已经存在ID为" + id + "的学生", "重复数据", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		// 包含空格
		if (id.contains(" ")) {
			JOptionPane.showMessageDialog(dialog, "ID中请不要包含空格！", "录入错误", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (name.contains(" ")) {
			JOptionPane.showMessageDialog(dialog, "名字中请不要包含空格！", "录入错误", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		// 用户输入了数据，再判断用户输入的数据是否合法
		for (char ch : id.toCharArray()) {
			if (ch > '9' || ch < '0') {
				JOptionPane.showMessageDialog(dialog, "您输入了一个无效ID，ID只能包含数字！", "录入错误", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		return true;
	}

}

package kj.view;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import kj.style.StyleBox;
import kj.util.IOUnit;
import kj.util.Picture;

/**
 * @description: 管理学生信息(增删改查)
 * @time: 2020-04-07 14:22:22
 * @author KONG
 */
public class ManageInterface extends JPanel {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -5892711421140136025L;

	/**
	 * 图片工具类
	 */
	private Picture picture;

	/**
	 * 标题
	 */
	private JLabel topic;

	/**
	 * 二维表，用于展现学生数据
	 */
	private StudentTable table;

	/**
	 * 添加信息按钮
	 */
	private JButton addButton;

	/**
	 * 保存按钮，用户编辑之后保存
	 */
	private JButton saveButton;

	/**
	 * 删除按钮
	 */
	private JButton deleteButton;

	/**
	 * 更改信息
	 */
	private JButton changeButton;

	/**
	 * 查询信息
	 */
	private JButton findButton;

	/**
	 * 记录信息
	 */
	private JButton infoButton;

	/**
	 * 构造方法
	 */
	public ManageInterface(StudentTable table) {
		picture = Picture.getInstance();
		this.table = table;
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		topic = new JLabel("学生信息管理", picture.getImageIcon("administrator.png"), JLabel.LEFT);
		//topic = new JLabel("学生信息管理");
		
		addButton = new JButton("添加信息");
		saveButton = new JButton("保存信息");
		deleteButton = new JButton("删除信息");
		changeButton = new JButton("更改信息");
		findButton = new JButton("查询学生");
		infoButton = new JButton("记录健康信息");
		setAttribute();
		addNode();
	}

	/**
	 * 设置节点属性
	 */
	private void setAttribute() {
		topic.setFont(StyleBox.MANAGE_TOPIC_FONT);
		topic.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		// 给保存按钮添加事件
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("保存按钮被触发");
				save();
			}
		});
		// 给添加按钮添加事件
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("添加按钮被触发");
				add();
			}
		});
		// 给删除按钮添加事件
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("删除按钮被触发");
				delete();
			}
		});
		// 给更改按钮添加事件
		changeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("更改按钮被触发");
				change();
			}
		});
		// 给查询按钮添加事件
		findButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("查询按钮被触发");
				find();
			}
		});
		infoButton.setBackground(StyleBox.REGISTER_BUTTON_BACKGROUND_COLOR);
		// 给查询按钮添加事件
		infoButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("记录健康信息按钮被触发");
				register();
			}
		});
	}

	/**
	 * 添加节点
	 */
	private void addNode() {
		this.setLayout(new GridLayout(1, 1));
		JPanel panel = new JPanel(new BorderLayout(1, 1)) {
			/**
			 * 序列号
			 */
			private static final long serialVersionUID = 6940454258707925565L;

			@Override
			protected void paintComponent(Graphics g) {
				g.drawImage(picture.getImageIcon("manageBackground.png").getImage(), 0, 0, this);
			}
		};
		// 垂直盒子，用于分开三组部件
		Box vBox = Box.createVerticalBox();
		vBox.add(topic);
		vBox.add(table);
		// 水平盒子，用于组装按钮部件
		Box buttonBox = Box.createHorizontalBox();
		buttonBox.add(Box.createHorizontalGlue());
		buttonBox.add(saveButton);
		buttonBox.add(Box.createHorizontalGlue());
		buttonBox.add(addButton);
		buttonBox.add(Box.createHorizontalGlue());
		buttonBox.add(deleteButton);
		buttonBox.add(Box.createHorizontalGlue());
		buttonBox.add(changeButton);
		buttonBox.add(Box.createHorizontalGlue());
		buttonBox.add(findButton);
		buttonBox.add(Box.createHorizontalGlue());
		buttonBox.add(infoButton);
		buttonBox.add(Box.createHorizontalGlue());
		vBox.add(buttonBox);
		panel.add(vBox);
		this.add(panel);
	}

	/**
	 * 保存数据
	 */
	public void save() {
		SwingWorker<Object, Boolean> task = new SwingWorker<Object, Boolean>() {

			@Override
			protected Object doInBackground() throws Exception {
				IOUnit.save(table.getAllData());
				return null;
			}

			@Override
			protected void done() {
				System.out.println("数据已保存");
				// 修改状态
				table.saved();
				JOptionPane.showMessageDialog(ManageInterface.this, "信息已保存！", "通知", JOptionPane.INFORMATION_MESSAGE);
			}

		};
		task.execute();
	}

	/**
	 * 添加数据
	 */
	private void add() {
		AddDialog addDialog = new AddDialog(table);
		addDialog.showDialog();
	}

	/**
	 * 删除数据
	 */
	private void delete() {
		DeleteDialog deleteDialog = new DeleteDialog(table);
		deleteDialog.showDialog();
	}

	/**
	 * 更改信息
	 */
	private void change() {
		ChangeDialog changeDialog = new ChangeDialog(table);
		changeDialog.showDialog();
	}

	/**
	 * 查询方法
	 */
	private void find() {
		// 唤起查询对话框
		FindDialog findDialog = new FindDialog(table);
		findDialog.showDialog();
	}

	/**
	 * 记录健康信息
	 */
	private void register() {
		INFODialog infoDialog = new INFODialog(table);
		infoDialog.showDialog();
	}

	/**
	 * 数据是否保存
	 * 
	 * @return
	 */
	public boolean isSaved() {
		return table.isSaved();
	}
}

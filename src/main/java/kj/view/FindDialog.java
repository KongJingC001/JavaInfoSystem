package kj.view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

import kj.style.StyleBox;

/**
 * @description: 查找对话框
 * @time: 2020-04-11 13:06:08
 * @author KONG
 */
public class FindDialog {

	/**
	 * 表列名
	 */
	private final Vector<String> columnNames = new Vector<String>();

	/**
	 * 对话框对象
	 */
	private JDialog dialog;

	/**
	 * 承载面板
	 */
	private JPanel panel;

	/**
	 * ID标签
	 */
	private JLabel idLabel;

	/**
	 * 输入框
	 */
	private JTextField idInput;

	/**
	 * 姓名标签
	 */
	private JLabel nameLabel;

	/**
	 * 输入框
	 */
	private JTextField nameInput;

	/**
	 * ID查找按钮
	 */
	private JButton idButton;

	/**
	 * 姓名查找按钮
	 */
	private JButton nameButton;

	/**
	 * 所有数据
	 */
	private final StudentTable datas;

	/**
	 * 检索二维表
	 */
	private JTable table;

	/**
	 * 盛放表格的容器
	 */
	private JScrollPane tablePane;

	/**
	 * 构造方法
	 * 
	 * @param datas - 所有学生信息
	 */
	public FindDialog(StudentTable datas) {
		dialog = new JDialog();
		idLabel = new JLabel("ID：");
		idInput = new JTextField(12);
		nameLabel = new JLabel("姓名：");
		nameInput = new JTextField(12);
		idButton = new JButton("查找");
		nameButton = new JButton("查找");
		this.datas = datas;
		initColumn();
		table = new JTable(datas.getAllData(), columnNames);
		tablePane = new JScrollPane(table);
		init();
	}

	/**
	 * 显示组件
	 */
	public void showDialog() {
		dialog.setVisible(true);
	}

	/**
	 * 初始化列名
	 */
	private void initColumn() {
		columnNames.add("序号");
		columnNames.add("姓名");
		columnNames.add("年龄");
		columnNames.add("性别");
		columnNames.add("何时加入该机构");
	}

	/**
	 * 初始化
	 */
	private void init() {
		dialog.setModal(true);
		dialog.setTitle("查找信息");
		dialog.setResizable(false);
		dialog.setSize(550, 500);
		dialog.setLocationRelativeTo(null);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setAttribute();
		setTableAttribute();
		addNode();
	}

	/**
	 * 设置属性
	 */
	private void setAttribute() {
		idButton.setBackground(StyleBox.BUTTON_BACKGROUND_COLOR);
		// 给查找按钮添加事件
		idButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("用户点击查询，开始检索信息");
				// 先获得用户输入的ID
				String id = idInput.getText();
				System.out.println("用户检索信息：" + id);
				// 重设table的信息，先删除盛放table的容器
				panel.remove(tablePane);
				// 检索输入框是否为空，为空则是整张表
				if (id == null || id.trim().isEmpty()) {
					table = new JTable(datas.getAllData(), columnNames);
				} else {
					System.out.println("用户检索id为" + id + "学生信息");
					// 重新构建数据表格
					table = new JTable(datas.findINFOById(id), columnNames);
				}
				// 重新加载容器
				tablePane = new JScrollPane(table);
				// 重新设置表格属性
				setTableAttribute();
				panel.add(tablePane);
				// 使用updateUI()进行重绘
				panel.updateUI();
			}
		});
		nameButton.setBackground(StyleBox.BUTTON_BACKGROUND_COLOR);
		// 给查找按钮添加事件
		nameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("用户点击查询，开始检索信息");
				// 先获得用户输入的姓名
				String name = nameInput.getText();
				System.out.println("用户检索信息：" + name);
				// 重设table的信息，先删除盛放table的容器
				panel.remove(tablePane);
				// 检索输入框是否为空，为空则是整张表
				if (name == null || name.trim().isEmpty()) {
					table = new JTable(datas.getAllData(), columnNames);
				} else {
					System.out.println("用户检索姓名为" + name + "学生信息");
					// 重新构建数据表格
					table = new JTable(datas.findINFOByName(name), columnNames);
				}
				// 重新加载容器
				tablePane = new JScrollPane(table);
				// 重新设置表格属性
				setTableAttribute();
				panel.add(tablePane);
				// 使用updateUI()进行重绘
				panel.updateUI();
			}
		});
	}

	/**
	 * 设置表格属性
	 */
	private void setTableAttribute() {
		// 设置表头
		table.getTableHeader().setResizingAllowed(false); // 设置不允许手动改变列宽
		table.getTableHeader().setReorderingAllowed(false); // 设置不允许拖动重新排序各列
		table.setPreferredScrollableViewportSize(new Dimension(500, 400));
		table.setFillsViewportHeight(true);
		table.setEnabled(false);
		// 设置表格中的数据居中显示
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		table.setDefaultRenderer(Object.class, r);
	}

	/**
	 * 添加节点
	 */
	private void addNode() {
		panel = new JPanel();
		panel.add(idLabel);
		panel.add(idInput);
		panel.add(idButton);
		panel.add(nameLabel);
		panel.add(nameInput);
		panel.add(nameButton);
		panel.add(tablePane);
		dialog.add(panel);
	}

}

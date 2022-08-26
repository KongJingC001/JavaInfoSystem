package kj.view;

import java.awt.Dimension;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import kj.model.Student;
import kj.model.TableData;

/**
 * description: 盛放table的面板
 * @author KONG
 */
public class StudentTable extends JScrollPane {

	/**
	 * 数据是否更新
	 */
	private boolean isSaved = true;

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1005205514116830076L;

	/**
	 * 二维表组件
	 */
	private final JTable table;

	/**
	 * 构造方法
	 * 
	 */
	public StudentTable(TableData tableData) {
		table = new JTable(tableData);
		init();
	}

	private void init() {
		table.setPreferredScrollableViewportSize(new Dimension(500, 400));
		table.setFillsViewportHeight(true);
		table.setEnabled(false);
		// 设置表格中的数据居中显示
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		table.setDefaultRenderer(Object.class, r);
		setTableHeader();
		this.setViewportView(table);
		setAttribute();
	}

	/**
	 * 设置表头
	 */
	private void setTableHeader() {
		// 设置不允许拖动表头顺序
		table.getTableHeader().setReorderingAllowed(false);
	}

	/**
	 * 给组件添加事件
	 */
	private void setAttribute() {

		// 监听数据改变
		table.getModel().addTableModelListener(e -> {
			System.out.println("数据发生改变");
			update();
		});

	}

	// ****************************************************//
	// 工具类

	/**
	 * 删除指定行数据
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String deleteINFOById(String id) {
		int row = -1;
		String target = null;
		Vector<Vector<Object>> data = getTableModel().getDataVector();
		// 获取所有存放学生信息的Vector的Vector
		Iterator<Vector<Object>> itr = data.iterator();
		// 开始遍历存放单个学生信息的Vector
		for (int i = 0; itr.hasNext(); i++) {
			// 得到单个学生信息
			Vector<Object> e = itr.next();
			// 对ID进行判断
			if (e.firstElement().equals(id)) {
				target = e.toString();
				row = i;
				break;
			}
		}
		if (row != -1) {
			getTableModel().removeRow(row);
		}
		return target;
	}

	/**
	 * 删除所有数据
	 */
	public void deleteAllINFO() {
		getTableModel().setRowCount(0);
	}

	/**
	 * 得到指定ID数据
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Vector<Vector<Object>> findINFOById(String id) {
		id = getStandardId(id);
		Vector<Vector<Object>> data = getTableModel().getDataVector();
		// 获取副本
		Vector<Vector<Object>> clone = new Vector<>(data);
		// 获取所有存放学生信息的Vector的Vector
		Iterator<Vector<Object>> itr = clone.iterator();
		// 开始遍历存放单个学生信息的Vector
		while (itr.hasNext()) {
			// 得到单个学生信息
			Vector<Object> e = itr.next();
			// 对ID进行判断
			if (!e.firstElement().equals(id)) {
				// 利用迭代器中的remove()方法删除，以免抛出异常
				itr.remove();
			}
		}
		return clone;
	}

	/**
	 * 得到指定名字数据
	 * 
	 */
	public Vector<Vector<Object>> findINFOByName(String name) {
		@SuppressWarnings("unchecked")
		Vector<Vector<Object>> data = getTableModel().getDataVector();
		// 获取副本
		Vector<Vector<Object>> clone = new Vector<>(data);
		// 获取所有存放学生信息的Vector的Vector
		// 开始遍历存放单个学生信息的Vector
		// 得到单个学生信息
		// 对ID进行判断
		// 利用迭代器中的remove()方法删除，以免抛出异常
		clone.removeIf(e -> !e.get(1).equals(name));
		return clone;
	}

	/**
	 * 添加一行信息
	 * 
	 */
	public String addINFO(Student rowData) {
		if (isFull())
			return null;
		getTableModel().addRow(rowData.getObjectArray());
		return rowData.toString();
	}

	/**
	 * 通过ID设置属性
	 * 
	 */
	public void setValueById(Student value, String id, String type) {
		int column = -1;
		switch (type) {
		case "名字":
			column = 1;
			break;
		case "年龄":
			column = 2;
			break;
		case "性别":
			column = 3;
			break;
		case "加入时间":
			column = 4;
		}
		setValueById(value, id, column);
	}

	/**
	 * 设置值
	 * 
	 */
	public void setValueById(Student value, String id, int column) {
		int row = rowOfId(id);
		if (row != 0) {
			getTableModel().setValueAt(value.getName(), row, column);
			getTableModel().setValueAt(value.getAge(), row, column);
			getTableModel().setValueAt(value.isSex(), row, column);
			getTableModel().setValueAt(value.getJoinTime(), row, column);
		}
	}

	public void setNameById(String name, String id) {
		int row = rowOfId(id);
		if (row != -1) {
			getTableModel().setValueAt(name, row, 1);
		}
	}

	public void setAgeById(int age, String id) {
		int row = rowOfId(id);
		if (row != -1) {
			getTableModel().setValueAt(age, row, 2);
		}
	}

	public void setSexById(String sex, String id) {
		int row = rowOfId(id);
		if (row != -1) {
			getTableModel().setValueAt(sex, row, 3);
		}
	}

	public void setJoinTimeById(String joinTime, String id) {
		int row = rowOfId(id);
		if (row != -1) {
			getTableModel().setValueAt(joinTime, row, 4);
		}
	}

	public void setBodyTEMPById(String bodyTEMP, String id) {
		int row = rowOfId(id);
		if (row != -1) {
			getTableModel().setValueAt(bodyTEMP, row, 5);
		}
	}

	public void setAddressById(String address, String id) {
		int row = rowOfId(id);
		if (row != -1) {
			getTableModel().setValueAt(address, row, 6);
		}
	}

	/**
	 * 通过ID得到行数
	 * 
	 */
	@SuppressWarnings("unchecked")
	public int rowOfId(String id) {
		id = getStandardId(id);
		System.out.print("查询是否包含ID为" + id + "的数据：");
		Vector<Vector<Object>> data = getTableModel().getDataVector();
		// 获取所有存放学生信息的Vector的Vector
		Iterator<Vector<Object>> itr = data.iterator();
		// 开始遍历存放单个学生信息的Vector
		for (int i = 0; itr.hasNext(); i++) {
			// 得到单个学生信息
			Vector<Object> e = itr.next();
			// 对ID进行判断
			if (e.firstElement().equals(id)) {
				System.out.println("存在！");
				return i;
			}
		}
		System.out.println("不存在！");
		return -1;
	}

	/**
	 * 得到表中所有的数据
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Vector<Student> getAllData() {
		return getTableModel().getDataVector();
	}

	/**
	 * 得到数据
	 * 
	 */
	public TableData getTableModel() {
		return (TableData) table.getModel();
	}

	/**
	 * 数据表中是否含有该项数据
	 * 
	 */
	@SuppressWarnings("unchecked")
	public boolean hasThisID(String id) {
		id = getStandardId(id);
		System.out.print("查询是否包含ID为" + id + "的数据：");
		Vector<Vector<Object>> data = getTableModel().getDataVector();
		// 获取所有存放学生信息的Vector的Vector
		// 开始遍历存放单个学生信息的Vector
		for (Vector<Object> e : data) {
			// 得到单个学生信息
			// 对ID进行判断
			if (e.firstElement().equals(id)) {
				System.out.println("存在！");
				return true;
			}
		}
		System.out.println("不存在！");
		return false;
	}

	/**
	 * 得到一个系统分配的ID
	 * 
	 * @return 返回一个ID号，如果返回null，则数据库已满无法添加
	 */
	public synchronized String getAutoID() {
		if (isFull())
			return null;
		Long idNum = 1L;
		// 遍历查找
		while (hasThisID(String.valueOf(idNum)))
			idNum++;
		String id = String.valueOf(idNum);
		id = getStandardId(id);
		return id;
	}

	@SuppressWarnings("rawtypes")
	public void addNewTable(Object obj) {
		if (obj == null)
			return;
		getTableModel().addRow((Vector) obj);
	}

	/**
	 * 判断是否达到最大数据容量
	 * 
	 */
	private boolean isFull() {
		return getTableModel().getDataVector().size() >= Math.pow(10, Student.getID_LENGTH());
	}

	/**
	 * 补足给定位数，得到标准ID
	 * 
	 */
	public String getStandardId(String id) {
		StringBuilder idBuilder = new StringBuilder(id);
		while (idBuilder.length() < Student.getID_LENGTH()) {
			idBuilder.insert(0, "0");
		}
		id = idBuilder.toString();
		return id;
	}

	/**
	 * ID是否合法
	 * 
	 */
	public boolean isLegalId(String id) {
		return isLegalId(null, id);
	}

	/**
	 * ID是否合法
	 * 
	 */
	public boolean isLegalId(java.awt.Component parent, String id) {
		// 检测是否为空
		if (id == null || id.trim().isEmpty()) {
			JOptionPane.showMessageDialog(parent, "请输入一个ID！", "空的输入", JOptionPane.WARNING_MESSAGE);
		}
		// 检测输入合法性
		if (id.length() > Student.getID_LENGTH()) {
			JOptionPane.showMessageDialog(parent, "不要输入超过" + Student.getID_LENGTH() + "位ID！", "输入不合法",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		// 包含空格
		if (id.contains(" ")) {
			JOptionPane.showMessageDialog(parent, "ID中请不要包含空格！", "录入错误", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		// 用户输入了数据，再判断用户输入的数据是否合法
		for (char ch : id.toCharArray()) {
			if (ch > '9' || ch < '0') {
				JOptionPane.showMessageDialog(parent, "您输入了一个无效ID，ID只能包含数字！", "录入错误", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		return true;
	}

	/**
	 * 检测用户ID是否输入过长
	 * 
	 */
	public boolean isTooLonger(String id) {
		return isTooLonger(null, id);
	}

	/**
	 * 检测用户ID是否输入过长
	 * 
	 */
	public boolean isTooLonger(java.awt.Component parent, String id) {
		if (id.length() > Student.getID_LENGTH()) {
			JOptionPane.showMessageDialog(parent, "请输入" + Student.getID_LENGTH() + "位以内ID！", "过长数据",
					JOptionPane.WARNING_MESSAGE);
			return true;
		}
		return false;
	}

	/**
	 * 数据发生了改变
	 */
	private void update() {
		setSaved(false);
	}

	public void saved() {
		setSaved(true);
	}

	public boolean isSaved() {
		return isSaved;
	}

	private void setSaved(boolean isSaved) {
		this.isSaved = isSaved;
	}

}

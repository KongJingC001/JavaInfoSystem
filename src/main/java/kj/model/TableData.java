package kj.model;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import kj.util.IOUnit;

/**
 * description: 表格数据
 * @author KONG
 */
public class TableData extends DefaultTableModel {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 66359621630817615L;

	private final Vector<String> columnNames = new Vector<>();

	/**
	 * 构造方法
	 */
	@SuppressWarnings("unchecked")
	public TableData() {
		Vector<Student> data;
		// 读取数据到表中
		Object obj = loadAllData();
		// 判断数据是否为空
		if (!(obj instanceof Vector<?>)) {
			data = new Vector<Student>();
		} else {
			data = (Vector<Student>) obj;
		}
		initColumn();
		this.setDataVector(data, columnNames);
	}

	/**
	 * 初始化列名
	 */
	private void initColumn() {
		columnNames.add("ID号");
		columnNames.add("姓名");
		columnNames.add("年龄");
		columnNames.add("性别");
		columnNames.add("何时加入该机构");
		columnNames.add("体温");
		columnNames.add("当前居住地址");
	}

	/**
	 * 从文件中读取数据
	 * 
	 */
	private Object loadAllData() {
		return IOUnit.load();
	}

}

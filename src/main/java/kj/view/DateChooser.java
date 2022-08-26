package kj.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * description: 日期选取器
 * @author KONG
 */
public class DateChooser extends JDialog {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 4003340082074296856L;

	/**
	 * 年月日信息
	 */
	private int year, month, day;
	/**
	 * 选择器控件
	 */
	private final JComboBox<Integer> yearBox;
	private final JComboBox<Integer> monthBox;
	private final JComboBox<Integer> dayBox;

	/**
	 * 确定取消按钮
	 */
	private final JButton confirm;
	private final JButton cancel;

	private final Calendar currentDate;

	/**
	 * 构造方法
	 */
	public DateChooser() {
		currentDate = Calendar.getInstance();
		yearBox = new JComboBox<Integer>(getNumbers(1970, currentDate.get(Calendar.YEAR)));
		monthBox = new JComboBox<Integer>(getNumbers(1, 12));
		dayBox = new JComboBox<Integer>(getNumbers(1, 31));
		confirm = new JButton("确认");
		cancel = new JButton("取消");
		this.init();
		this.setVisible(true);
	}

	/**
	 * 初始化
	 */
	private void init() {
		this.setModal(true);
		this.setTitle("选择日期");
		this.setResizable(false);
		this.setSize(200, 100);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setAttribute();
		this.addNode();
	}

	/**
	 * 设置节点属性
	 */
	private void setAttribute() {
		// 设置默认选择
		yearBox.setSelectedItem(Integer.valueOf(currentDate.get(Calendar.YEAR)));
		monthBox.setSelectedItem(Integer.valueOf(currentDate.get(Calendar.MONTH) + 1));
		dayBox.setSelectedItem(Integer.valueOf(currentDate.get(Calendar.DAY_OF_MONTH)));
		// 设置初始化值
		year = Integer.MIN_VALUE;
		month = year;
		day = month;
		// 给按钮添加监听器
		this.confirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.print("用户确定了一个日期:");
				year = (Integer) yearBox.getSelectedItem();
				month = (Integer) monthBox.getSelectedItem();
				day = (Integer) dayBox.getSelectedItem();
				if (!isLegalDate(year, month, day)) {
					System.out.println("该日期不合法");
					year = Integer.MIN_VALUE;
					month = year;
					day = month;
				}
				System.out.println(getDate());
				DateChooser.this.dispose();
			}

		});
		this.cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("用户取消选择日期");
				DateChooser.this.dispose();
			}
		});

	}

	/**
	 * 添加节点
	 */
	private void addNode() {
		JPanel panel = new JPanel();
		panel.add(yearBox);
		panel.add(monthBox);
		panel.add(dayBox);
		panel.add(confirm);
		panel.add(cancel);
		this.setContentPane(panel);
	}

	/**
	 * 得到年信息
	 * 
	 * @return
	 */
	public int getYear() {
		return year;
	}

	/**
	 * 得到月信息
	 * 
	 * @return
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * 得到日信息
	 * 
	 * @return
	 */
	public int getDay() {
		return day;
	}

	/**
	 * 得到字符串化的日期：用“-”分隔
	 * 
	 * @return - 如果不合法或者取消选定则返回null
	 */
	public String getDate() {
		if (year == Integer.MIN_VALUE || month == Integer.MIN_VALUE || day == Integer.MIN_VALUE) {
			return null;
		}
		return getYear() + "-" + getMonth() + "-" + getDay();
	}

	/**
	 * 检测用户年月日组合是否合法
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	private boolean isLegalDate(int year, int month, int day) {
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			if (day > 31) {
				return false;
			}
			break;
		case 2:
			// 当年是否为闰年，如果不是闰年，不允许2月有29天
			if (!((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0 && year % 3200 != 0) || year % 172800 == 0)) {
				if (day == 29) {
					return false;
				}
			}
			// 闰年二月最多29天
			if (day > 29) {
				return false;
			}
			break;
		case 4:
		case 6:
		case 9:
		case 11:
			if (day > 30) {
				return false;
			}
			break;
		}
		return true;
	}

	/**
	 * 得到数字串数组
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static Integer[] getNumbers(int min, int max) {
		if (max < min) {
			int temp = max;
			max = min;
			min = temp;
		}
		Integer[] nums = new Integer[max - min + 1];
		for (int i = 0; i < nums.length; i++) {
			nums[i] = min + i;
		}
		return nums;
	}

}

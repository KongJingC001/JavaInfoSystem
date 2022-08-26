package kj.style;

import java.awt.Color;
import java.awt.Font;

/**
 * description: 样式盒
 * @author KONG
 */
public interface StyleBox {

	/**
	 * 选项卡面板背景色
	 */
	public static final Color OPTION_BACKGROUND_COLOR = Color.WHITE;
	/**
	 * 选项卡按钮颜色
	 */
	public static final Color OPTION_BUTTON_COLOR = new Color(225, 230, 246);
	/**
	 * 选项卡按钮字体
	 */
	public static final Font OPTION_BUTTON_FONT = new Font("微软雅黑", Font.BOLD, 16);

	/**
	 * 管理界面标题字体
	 */
	public static final Font MANAGE_TOPIC_FONT = new Font("宋体", Font.BOLD, 60);

	/**
	 * 对话框字体
	 */
	public static final Font DIALOG_FONT = new Font(null, Font.PLAIN, 16);

	/**
	 * 对话框提示字体
	 */
	public static final Font DIALOG_HINT = new Font("微软雅黑", Font.BOLD, 18);

	/**
	 * 对话框提示背景色
	 */
	public static final Color HINT_FOREGROUND_COLOR = Color.RED;

	/**
	 * 按钮背景色
	 */
	public static final Color BUTTON_BACKGROUND_COLOR = new Color(225, 225, 225);

	/**
	 * 记录健康信息按钮背景色
	 */
	public static final Color REGISTER_BUTTON_BACKGROUND_COLOR = Color.RED;

}

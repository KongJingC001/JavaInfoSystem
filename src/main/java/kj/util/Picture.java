package kj.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.swing.ImageIcon;

/**
 * description: 工具类-加载图片
 * @author KONG
 */
public final class Picture {

	/**
	 * 实例
	 */
	private static Picture picture;

	/**
	 * 图片文件所在包名
	 */
	private static final String PACKAGE_NAME = "image/";

	/**
	 * 图片文件名
	 */
	private final String[] PICTURE_LIST = { "icon.png", "administrator.png", "manageBackground.png" };

	/**
	 * 存放图片实例集合
	 */
	private final Map<String, ImageIcon> pictureMap;

	/**
	 * 构造方法
	 */
	private Picture() {
		pictureMap = new HashMap<>();
		System.out.println("正在加载图片");
		// 加载所有图片
		for (String s : PICTURE_LIST) {
			System.out.println("图片：" + s);
			loadImage(PACKAGE_NAME + s);
		}
		System.out.println("图片加载完毕");
	}

	/**
	 * 使用单例模式
	 * 
	 */
	public static Picture getInstance() {
		if (picture == null) {
			picture = new Picture();
		}
		return picture;
	}

	/**
	 * 读取图片实例
	 * 
	 */
	private ImageIcon loadImage(String filePath) {
		if (pictureMap.containsKey(filePath)) {
			return pictureMap.get(filePath);
		}
		System.out.println(filePath);
		ImageIcon pic = new ImageIcon(Objects.requireNonNull(Picture.class.getClassLoader().getResource(filePath)));
		return pictureMap.put(filePath, pic);
	}

	/**
	 * 通过名称获得图片实例
	 * 
	 */
	public ImageIcon getImageIcon(String name) {
		return loadImage(PACKAGE_NAME + name);
	}
}

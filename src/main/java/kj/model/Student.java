package kj.model;

/**
 * description: 学生类
 * @author KONG
 */
public class Student {

	/**
	 * ID位数
	 */
	private final static int ID_LENGTH = 8;

	private String id;
	private String name;
	private Integer age;
	private boolean sex;
	private String joinTime;
	private String address;
	private int temperature;

	public Student() {
	}

	public Student(String id, String name, int age, boolean sex, String joinTime) {
		super();
		this.setId(id);
		this.setName(name);
		this.setAge(age);
		this.setSex(sex);
		this.setJoinTime(joinTime);
	}

	@Override
	public String toString() {
		return id + "." + name + ":" + age + "、" + sex + "、" + joinTime;
	}

	/**
	 * 得到数组形式的对象
	 * 
	 */
	public Object[] getObjectArray() {
		return new Object[] { id, name, age, sex ? "男" : "女", joinTime };
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public String getJoinTime() {
		return joinTime;
	}

	private void setJoinTime(String joinTime) {
		this.joinTime = joinTime;
	}

	public void setJoinTime(int year, int month, int day) {
		this.joinTime = year + "-" + month + "-" + day;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static int getID_LENGTH() {
		return ID_LENGTH;
	}
}

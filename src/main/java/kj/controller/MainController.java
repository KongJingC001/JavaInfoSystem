package kj.controller;

import kj.view.MainInterface;

/**
 * description: 主控制器
 * time: 2020-04-07 13:41:23
 * @author KONG
 */
public class MainController {

    /**
     * 主面板
     */
    private final MainInterface mainInterface;

    /**
     * 构造方法
     *
     */
    public MainController(MainInterface mainInterface) {
        System.out.println("核心控制器加载成功！");
        this.mainInterface = mainInterface;
    }

    /**
     * 启动GUI
     */
    public void start() {
        System.out.println("界面已启动！");
        mainInterface.start();
    }
}

package cn.dante.p10adapter.example3;

import java.util.ArrayList;
import java.util.List;

public class Client {
	public static void main(String[] args) {
		//准备日志内容，也就是测试的数据
		LogModel lm1 = new LogModel();
		lm1.setLogId("001");
		lm1.setOperateUser("admin");
		lm1.setOperateTime("2010-03-02 10:08:18");
		lm1.setLogContent("这是一个测试");
		
		List<LogModel> list = new ArrayList<LogModel>();
		list.add(lm1);

		//创建操作日志文件的对象
		LogFileOperateApi logFileOperateApi = new LogFileOperate("");

		//
		LogDbOperateApi api = new MyAdapter(logFileOperateApi,new TimeUtil());

		//操作数据库的接口能兼容实现文件的功能
//		LogDbOperateApi api = new DBIMPL();
		//保存日志文件
		api.createLog(lm1);

		//读取日志文件的内容
		List<LogModel> allLog = api.getAllLog();
		System.out.println("allLog="+allLog);

	}
}

package cn.itcast;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.view.JasperViewer;

import java.util.HashMap;

public class JrDemo {
    public static void main(String[] args) {

            createJrprint();
//        createJasper();
//        showPdf();
    }
    // 1.将pdf模板编译为Jasper文件
    public static void createJasper(){
        try{
            String path = "C:\\Users\\admin\\Desktop\\test\\t03\\test01.jrxml";
            JasperCompileManager.compileReportToFile(path);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // 2.将Jasper文件和数据进行填充
    public static void createJrprint(){
        try{
            String path = "C:\\Users\\admin\\Desktop\\test\\t03\\test01.jasper";
            //通过空参数和空数据源进行填充
            JasperFillManager.fillReportToFile(path,new HashMap(),new JREmptyDataSource());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // 3.预览
    public static void showPdf(){
        try{
            String path = "C:\\Users\\admin\\Desktop\\test\\t03\\test01.jrprint";
            JasperViewer.viewReport(path,false);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}

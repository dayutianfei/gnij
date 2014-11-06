package cn.ac.iie.s3.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Mysql {

    /**
     * 入口函数
     * @param arg
     */
    public static void main(String arg[]) {
        try {
            Connection con = null; //定义一个MYSQL链接对象
            Class.forName("com.mysql.jdbc.Driver").newInstance(); //MYSQL驱动
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/metastorejs1031", "root", "root"); //链接本地MYSQL

            Statement stmt; //创建声明
            stmt = con.createStatement();

            //获取问题FILES的ID
            ResultSet res = stmt.executeQuery("select FILE_ID from FILES where SPLITKEY = 'default'");
            List<Long> ret_id = new ArrayList<Long>();
            while (res.next()) {
               long  ret_id_temp = res.getLong(1);
                ret_id.add(ret_id_temp);
            }
            System.out.println(ret_id.size());
            int countall = 0;
            for(long i:ret_id){
            	//查询数据并输出
            	System.out.println(i);
                String selectSql = "SELECT * FROM FILE_KEY_VALS where FILE_ID = "+i + " order by INTEGER_IDX asc";
                ResultSet selectRes = stmt.executeQuery(selectSql);
                String[] content = new String[2];
                int ii = 0;
                while (selectRes.next()) { //循环输出结果集
                    String key = selectRes.getString("PKEY_NAME");
                    String value = selectRes.getString("VALUE");
                    content[ii]=key+"="+value;
                    ii++;
                }
                String temp = content[0]+"/"+content[1];
                stmt.executeUpdate("UPDATE FILES set SPLITKEY = '"+temp+"' where FILE_ID = "+i);
                countall ++;
                System.out.println(countall +"==>"+ret_id.size());
            }
        } catch (Exception e) {
            System.out.print("MYSQL ERROR:" + e.getMessage());
            e.printStackTrace();
        }

    }
}


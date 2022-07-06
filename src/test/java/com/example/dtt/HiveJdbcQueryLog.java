package com.example.dtt;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.apache.hive.jdbc.HiveStatement;

import java.sql.*;
import java.util.List;

public class HiveJdbcQueryLog {
  public static void main(String[] args) throws Exception {
      Class.forName("org.apache.hive.jdbc.HiveDriver");
      Connection connection = DriverManager.getConnection("jdbc:hive2://10.42.0.122:10000", "root", "root");
      HiveStatement stmt = (HiveStatement) connection.createStatement();

      String sql = "select count(*) from im_financial_pdca_bu";

      try {
          Thread logThread = new Thread(new LogTask(stmt));
          logThread.setDaemon(true);
          logThread.start();
          boolean hasResult = stmt.execute(sql);
          logThread.interrupt();
          if (hasResult) {
              ResultSet resultSet = stmt.getResultSet();
              ResultSetMetaData metaData = resultSet.getMetaData();
              int colCount = metaData.getColumnCount();
              List<String> res = Lists.newArrayList();
              while (resultSet.next()) {
                  List<String> row = Lists.newArrayList();
                  for (int i = 1; i <= colCount; i++) {
                      row.add(resultSet.getString(i));
                  }
                  res.addAll(row);
              }
              System.out.println(JSON.toJSONString(res));
          } else {
              stmt.getUpdateCount();
          }
      } catch (Exception ex) {
          ex.printStackTrace();
      }finally {
          stmt.close();
          connection.close();
      }
  }

  public static class LogTask implements Runnable {
      private HiveStatement stmt;
      public LogTask(HiveStatement stmt) {
          this.stmt = stmt;
      }
      @Override
      public void run() {
          try {
              while (stmt.hasMoreLogs()) {
                  try {
                      for (String line : stmt.getQueryLog(true, 1000)) {
                          System.out.println(line);
                      }
                      Thread.sleep(200);
                  } catch (SQLException e) {
                      e.printStackTrace();
                  }
              }
          } catch (Exception e) {
              e.printStackTrace();
          }
      }
  }
}

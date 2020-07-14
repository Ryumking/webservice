//package controller;
//
//import dao.DataSource;
//import lombok.SneakyThrows;
//import org.apache.ibatis.jdbc.ScriptRunner;
//
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;
//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.Reader;
//
//@WebListener
//public class ContextListener implements ServletContextListener {
//    @SneakyThrows
//    @Override
//    public void contextInitialized(ServletContextEvent servletContextEvent) {
//        ScriptRunner sr=new ScriptRunner(DataSource.getConnection());
//        Reader reader=new BufferedReader(new FileReader("db.sql"));
//        sr.runScript(reader);
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent servletContextEvent) {
//
//    }
//}

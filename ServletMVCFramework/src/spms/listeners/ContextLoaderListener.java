package spms.listeners;

// 프로퍼티 파일 적용 : ApplicationContext 사용
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import spms.context.ApplicationContext;

@WebListener
public class ContextLoaderListener implements ServletContextListener {
  static ApplicationContext applicationContext;
  
  public static ApplicationContext getApplicationContext() {
	  //ContextLoaderListener에서 만든 ApplicationContext객체를 얻을 때 사용.(프런트 컨트롤러에서 사용)
	  //static으로 선언
    return applicationContext;
  }
  
  @Override
  
  public void contextInitialized(ServletContextEvent event) {
    try {
      ServletContext sc = event.getServletContext();
      
      String propertiesPath = sc.getRealPath(
          sc.getInitParameter("contextConfigLocation"));
      //프로퍼티 파일의 이름과 경로 정보를 web.xml파일로부터 읽어 오게 처리함 (getInitParameter)
      //web.xml에 context-param으로 contextconfigLocation 정의
      applicationContext = new ApplicationContext(propertiesPath);
      
    } catch(Throwable e) {
      e.printStackTrace();
    }
  }
  
  @Override
  public void contextDestroyed(ServletContextEvent event) {}
}

package spms.listeners;

// ������Ƽ ���� ���� : ApplicationContext ���
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import spms.context.ApplicationContext;

@WebListener
public class ContextLoaderListener implements ServletContextListener {
  static ApplicationContext applicationContext;
  
  public static ApplicationContext getApplicationContext() {
	  //ContextLoaderListener���� ���� ApplicationContext��ü�� ���� �� ���.(����Ʈ ��Ʈ�ѷ����� ���)
	  //static���� ����
    return applicationContext;
  }
  
  @Override
  
  public void contextInitialized(ServletContextEvent event) {
    try {
      ServletContext sc = event.getServletContext();
      
      String propertiesPath = sc.getRealPath(
          sc.getInitParameter("contextConfigLocation"));
      //������Ƽ ������ �̸��� ��� ������ web.xml���Ϸκ��� �о� ���� ó���� (getInitParameter)
      //web.xml�� context-param���� contextconfigLocation ����
      applicationContext = new ApplicationContext(propertiesPath);
      
    } catch(Throwable e) {
      e.printStackTrace();
    }
  }
  
  @Override
  public void contextDestroyed(ServletContextEvent event) {}
}

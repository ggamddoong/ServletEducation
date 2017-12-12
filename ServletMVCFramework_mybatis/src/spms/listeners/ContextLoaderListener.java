package spms.listeners;
 
// SqlSessionFactory ��ü �غ�
import java.io.InputStream;
 
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
 
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
 
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
            //ApplicationContext ��ü�� ������ �� �⺻ �����ڸ� ȣ���ϵ��� �ڵ带 ����
            applicationContext = new ApplicationContext();
 
            //SqlSessionFactoryBuilder Ŭ������ build()�� ȣ���ؾ߸� SqlSessionFactory ��ü�� ������ �� �ִ�.
            String resource = "spms/dao/mybatis-config.xml";
            //mybatis���� �����ϴ� Resources Ŭ������ ����ϸ� �ڹ� Ŭ���� ��ο� �ִ� ������ �Է� ��Ʈ���� ���� ���� �� �ִ�.
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
 
            //SqlSessionFactory ��ü�� ApplicationContext�� ����Ѵ�.
            applicationContext.addBean("sqlSessionFactory", sqlSessionFactory);
 
            //������Ƽ ����(aqpplication-context.properties) ��θ� �˾Ƴ���
            //������Ƽ ������ �̸��� ��� ������ web.xml���Ϸκ��� �о� ���� ó���� (getInitParameter)
            //web.xml�� context-param���� contextconfigLocation ����            
            ServletContext sc = event.getServletContext();
            String propertiesPath = sc.getRealPath(sc.getInitParameter("contextConfigLocation"));
 
            //������Ƽ ������ ���뿡 ���� ��ü�� �����ϵ��� ApplicationContext�� �����Ѵ�.
            applicationContext.prepareObjectsByProperties(propertiesPath);
 
            //@Component ������̼��� ���� Ŭ������ ã�� ��ü�� �����Ѵ�.
            applicationContext.prepareObjectsByAnnotation("");
 
            //ApplicationContext���� �����ϴ� ��ü���� �����Ͽ� ���� ��ü�� �����Ѵ�.
            applicationContext.injectDependency();
 
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
 
    @Override
    public void contextDestroyed(ServletContextEvent event) {
    }
}

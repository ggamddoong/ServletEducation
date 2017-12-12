package spms.context;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.reflections.Reflections;

import spms.annotation.Component;

// ������Ƽ ���� �� �ֳ����̼��� �̿��� ��ü �غ�
public class ApplicationContext {
  Hashtable<String,Object> objTable = new Hashtable<String,Object>();
  //������Ƽ�� ������ ��� ��ü�� �غ��ϸ�, ��ü�� ������ �����Ұ� �ʿ��ѵ� �̸� ���� �ؽ� ���̺��� �غ�

  //����, �Ʒ�ó�� �ؽ� ���̺��� ��ü�� ���� �޼��嵵 ����
  public Object getBean(String key) {
    return objTable.get(key);
  }
  
  public ApplicationContext(String propertiesPath) throws Exception {
    Properties props = new Properties();
    props.load(new FileReader(propertiesPath));
    
    prepareObjects(props); //Properties
    prepareAnnotationObjects(); //������̼����� ���ǵ� Ŭ������ �ν��Ͻ�ȭ
    injectDependency(); //��ü�� �ʿ�� �ϴ� ������ü�� �Ҵ����ִ� �Լ�
  }
  
  private void prepareAnnotationObjects() 
      throws Exception{
    Reflections reflector = new Reflections("");//Reflections�� �����ҽ����̺귯��,
    //�ڹٿ��� �����ϴ� ���÷��� API���� �� ���� Ŭ������ ã�ų� Ŭ���� ������ ���� 
    //"" ���ڿ� �Ű������� �ڹ� classpath�� �ִ� ��� ��Ű�� �˻�
    //���� �Ű������� miniMVCFramework���ٸ�  miniMVCFramework ��Ű�� �� �� ���� ��Ű������ ��� ����
    
    Set<Class<?>> list = reflector.getTypesAnnotatedWith(Component.class);
    //@Componetn ������̼Ŵ� ���� Ŭ������ ã������� ���� �ڵ�ó�� ������̼� Ŭ������ �����ϸ� ��.
    //��ȯ�Ǵ� ���� @Component ������̼Ŵ� �����Ŭ���� �����.
    String key = null;
    for(Class<?> clazz : list) {
      key = clazz.getAnnotation(Component.class).value();
      objTable.put(key, clazz.newInstance());
    }
  }

  private void prepareObjects(Properties props) throws Exception {
    Context ctx = new InitialContext();
    String key = null;
    String value = null;
    
    for (Object item : props.keySet()) {
      key = (String)item;
      value = props.getProperty(key);
      if (key.startsWith("jndi.")) { //DataSourceó�� ��Ĺ �������� �����ϴ� ��ü�� ApplicationContext���� 
    	  //������ �� ����. ��� InitialContext�� ���� �ش� ��ü�� ���� �Ѵ�.
        objTable.put(key, ctx.lookup(value));
      } else {
        objTable.put(key, Class.forName(value).newInstance());
      }
    }
  }
  
  private void injectDependency() throws Exception {
    for (String key : objTable.keySet()) {
      if (!key.startsWith("jndi.")) { //jndi�� �����ϴ� ��� ��Ĺ �������� ������ ��ü�̹Ƿ� ���� ��ü �����ؼ��� �ȵȴ�.
        callSetter(objTable.get(key));//�Ű������� �־��� ��ü�� ���� ���� �޼��带 ã�Ƽ� ȣ���ϴ� ��
      }
    }
  }

  private void callSetter(Object obj) throws Exception {
    Object dependency = null;
    for (Method m : obj.getClass().getMethods()) {
      if (m.getName().startsWith("set")) {
        dependency = findObjectByType(m.getParameterTypes()[0]);
        //�� �޼���� ���� �޼��带 ȣ���� �� �Ѱ��� ���� ��ü�� ã�� ���� �Ѵ�. objTable�� ��� �ִ� ��ü�� ��� ������.
      //���� ���� �޼����� �Ű����� Ÿ�Ӱ� ��ġ�ϴ� ��ü�� ã�Ҵٸ� �� ��ü�� �ּҸ� �����Ѵ�.
        if (dependency != null) {
          m.invoke(obj, dependency);
          
        }
      }
    }
  }
  
  private Object findObjectByType(Class<?> type) {
    for (Object obj : objTable.values()) {
      if (type.isInstance(obj)) {
        return obj;
      }
    }
    return null;
  }
}

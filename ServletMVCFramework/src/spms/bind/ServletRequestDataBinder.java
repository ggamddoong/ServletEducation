package spms.bind;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Set;

import javax.servlet.ServletRequest;

public class ServletRequestDataBinder {
	//�� Ŭ������ �ܺο��� ȣ���� �� �ִ� �� ���� public �޼���� ���ο��� ����� ������ private �޼��带 �������ִ�.
	//��� static
  public static Object bind(
      ServletRequest request, Class<?> dataType, String dataName) 
      throws Exception {
    if (isPrimitiveType(dataType)) { //dataType�� �⺻Ÿ������ �˻�, �⺻Ÿ���̸� ��� ��ü �����Ͽ� ��ȯ��
    	//�⺻Ÿ���� �߰��Ϸ��� isPrimitiveType �Լ��ȿ��� �� �߰� (��: byte, short)
      return createValueObject(dataType, request.getParameter(dataName));
      //createValueObject()�� �⺻ Ÿ���� ��ü�� ������ �� ȣ��
    }
    
    Set<String> paramNames = request.getParameterMap().keySet();
    //request.getParameterMap()�� �Ű�����(Ŭ���̾�Ʈ���� �Ѿ�� ��)�� �̸��� ���� �� ��ü�� ��Ƽ� ��ȯ�Ѵ�.
    //�츮�� �ʿ��� ���� �Ű������� �̸��̱� ������ Map�� keySey()�� ȣ��
    Object dataObject = dataType.newInstance(); 
    //newInstance()�� new �����ڸ� ������� �ʰ� ��ü ����
    Method m = null;
    
    for (String paramName : paramNames) {
    	//������ Ÿ�� Ŭ�������� �Ű����� �̸��� ��ġ�ϴ� ������Ƽ(���� �޼���)��ã���ϴ�.
      
    	m = findSetter(dataType, paramName);
    	//������ Ÿ��(Class)�� �Ű����� �̸�(String)�� �ָ� ���� �޼��带 ã�Ƽ� ��ȯ�մϴ�.
    	//���� �޼��带 ã������ ������ ������ dataObject�� ���� ȣ���մϴ�.
      if (m != null) {
        m.invoke(dataObject, createValueObject(m.getParameterTypes()[0], 
            request.getParameter(paramName)));
        //createValueObject()�޼���� �տ��� ������ �ٿ� ����, 
        //��û �Ű������� ���� ������ �⺻ Ÿ���� ��ü�� ����� �ݴϴ�.
        //�̷��� ��û �Ű������� ������ŭ �ݺ��ϸ鼭, ������ ��ü(��:Member)�� ���� ���� �Ҵ��մϴ�.
        
        //Method.invoke() <- �ش� �޼��带 ȣ��
      }
    }
    return dataObject;
  }
  
  private static boolean isPrimitiveType(Class<?> type) {
    if (type.getName().equals("int") || type == Integer.class ||
        type.getName().equals("long") || type == Long.class ||
        type.getName().equals("float") || type == Float.class ||
        type.getName().equals("double") || type == Double.class ||
        type.getName().equals("boolean") || type == Boolean.class ||
        type == Date.class || type == String.class) {
      return true;
    }
    return false;
  }
  
  private static Object createValueObject(Class<?> type, String value) {
	  //�⺻ Ÿ���� ��� ���� �޼��尡 ���� ������ ���� �Ҵ社�� �� �����ϴ�.
	  //���� �����ڸ� ȣ���� �� ���� �Ҵ��մϴ�. �׷��� createValueObject()�� ���� ���Դϴ�.
	  //�� �޼���� ���ͷ� ���� �Ҵ��� �� ���� �⺻ Ÿ�Կ� ���� ��ü�� �����ϴ� �޼����Դϴ�.
    if (type.getName().equals("int") || type == Integer.class) {
      return new Integer(value);
    } else if (type.getName().equals("float") || type == Float.class) {
      return new Float(value);
    } else if (type.getName().equals("double") || type == Double.class) {
      return new Double(value);
    } else if (type.getName().equals("long") || type == Long.class) {
      return new Long(value);
    } else if (type.getName().equals("boolean") || type == Boolean.class) {
      return new Boolean(value);
    } else if (type == Date.class) {
      return java.sql.Date.valueOf(value);
    } else {
      return value;
    }
  }
  
  private static Method findSetter(Class<?> type, String name) {
    Method[] methods = type.getMethods();
    
    String propName = null;
    for (Method m : methods) {
      if (!m.getName().startsWith("set")) continue;
      //���� �޼��忡 ���ؼ��� �۾��� �����ϱ� ������ �޼��� �̸��� set���� �������� �ʴ´ٸ� ����.
      
      propName = m.getName().substring(3);
      if (propName.toLowerCase().equals(name.toLowerCase())) {
    	  //��ҹ��ڸ� �������� �ʱ����� ��� �ҹ��ڷ� �ٲ� ������ ��
    	  //�׸��� ���� �޼����� �̸����� "set"�� �����ϰ� ����.
        return m;
      }
    }
    return null;
  }
}

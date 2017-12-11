package spms.bind;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Set;

import javax.servlet.ServletRequest;

public class ServletRequestDataBinder {
	//이 클래스는 외부에서 호출할 수 있는 한 개의 public 메서드와 내부에서 사용할 세개의 private 메서드를 가지고있다.
	//모두 static
  public static Object bind(
      ServletRequest request, Class<?> dataType, String dataName) 
      throws Exception {
    if (isPrimitiveType(dataType)) { //dataType이 기본타입인지 검사, 기본타입이면 즉시 객체 생성하여 반환함
    	//기본타입을 추가하려면 isPrimitiveType 함수안에서 더 추가 (예: byte, short)
      return createValueObject(dataType, request.getParameter(dataName));
      //createValueObject()는 기본 타입의 객체를 생성할 때 호출
    }
    
    Set<String> paramNames = request.getParameterMap().keySet();
    //request.getParameterMap()는 매개변수(클라이언트에서 넘어온 값)의 이름과 값을 맵 객체에 담아서 반환한다.
    //우리가 필요한 것은 매개변수의 이름이기 때문에 Map의 keySey()을 호출
    Object dataObject = dataType.newInstance(); 
    //newInstance()는 new 연산자를 사용하지 않고도 객체 생성
    Method m = null;
    
    for (String paramName : paramNames) {
    	//데이터 타입 클래스에서 매개변수 이름과 일치하는 프로퍼티(셋터 메서드)를찾습니다.
      
    	m = findSetter(dataType, paramName);
    	//데이터 타입(Class)과 매개변수 이름(String)을 주면 셋터 메서드를 찾아서 반환합니다.
    	//셋터 메서드를 찾았으면 이전에 생성한 dataObject에 대해 호출합니다.
      if (m != null) {
        m.invoke(dataObject, createValueObject(m.getParameterTypes()[0], 
            request.getParameter(paramName)));
        //createValueObject()메서드는 앞에서 설명한 바와 같이, 
        //요청 매개변수의 값을 가지고 기본 타입의 객체를 만들어 줍니다.
        //이렇게 요청 매개변수의 개수만큼 반복하면서, 데이터 객체(예:Member)에 대해 값을 할당합니다.
        
        //Method.invoke() <- 해당 메서드를 호출
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
	  //기본 타입의 경우 셋터 메서드가 없기 때문에 값을 할당ㅎㄹ 수 없습니다.
	  //보통 생성자를 호출할 때 값을 할당합니다. 그래서 createValueObject()를 만든 것입니다.
	  //이 메서드는 셋터로 값을 할당할 수 없는 기본 타입에 대해 객체를 생성하는 메서드입니다.
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
      //셋터 메서드에 대해서만 작업을 수행하기 때문에 메서드 이름이 set으로 시작하지 않는다면 무시.
      
      propName = m.getName().substring(3);
      if (propName.toLowerCase().equals(name.toLowerCase())) {
    	  //대소문자를 구분하지 않기위해 모두 소문자로 바꾼 다음에 비교
    	  //그리고 셋터 메서드의 이름에서 "set"은 제외하고 리턴.
        return m;
      }
    }
    return null;
  }
}

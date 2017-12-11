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

// 프로퍼티 파일 및 애노테이션을 이용한 객체 준비
public class ApplicationContext {
  Hashtable<String,Object> objTable = new Hashtable<String,Object>();
  //프로퍼티에 설정된 대로 객체를 준비하면, 객체를 저장할 보관소가 필요한데 이를 위해 해시 테이블을 준비

  //또한, 아래처럼 해시 테이블에서 객체를 꺼낼 메서드도 정의
  public Object getBean(String key) {
    return objTable.get(key);
  }
  
  public ApplicationContext(String propertiesPath) throws Exception {
    Properties props = new Properties();
    props.load(new FileReader(propertiesPath));
    
    prepareObjects(props); //Properties
    prepareAnnotationObjects(); //어노테이션으로 정의된 클래스를 인스턴스화
    injectDependency(); //객체가 필요로 하는 의존객체를 할당해주는 함수
  }
  
  private void prepareAnnotationObjects() 
      throws Exception{
    Reflections reflector = new Reflections("");//Reflections는 오프소스라이브러리,
    //자바에서 제공하는 리플랙션 API보다 더 쉽게 클래스를 찾거나 클래스 정보를 추출 
    //"" 빈문자열 매개변수는 자바 classpath에 있는 모든 패키지 검색
    //만약 매개변수가 miniMVCFramework였다면  miniMVCFramework 패키지 및 그 하위 패키지까지 모두 뒤짐
    
    Set<Class<?>> list = reflector.getTypesAnnotatedWith(Component.class);
    //@Componetn 어노테이셔닝 붙은 클래스를 찾고싶으면 앞의 코드처럼 어노테이션 클래스를 지정하면 됨.
    //반환되는 값은 @Component 어노테이셔니 선언된클래스 목록임.
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
      if (key.startsWith("jndi.")) { //DataSource처럼 톰캣 서버에서 제공하는 객체는 ApplicationContext에서 
    	  //생성할 수 없다. 대신 InitialContext를 통해 해당 객체를 얻어야 한다.
        objTable.put(key, ctx.lookup(value));
      } else {
        objTable.put(key, Class.forName(value).newInstance());
      }
    }
  }
  
  private void injectDependency() throws Exception {
    for (String key : objTable.keySet()) {
      if (!key.startsWith("jndi.")) { //jndi로 시작하는 경우 톰캣 서버에서 제공한 객체이므로 의존 객체 주입해서는 안된다.
        callSetter(objTable.get(key));//매개변수로 주어진 객체에 대해 셋터 메서드를 찾아서 호출하는 일
      }
    }
  }

  private void callSetter(Object obj) throws Exception {
    Object dependency = null;
    for (Method m : obj.getClass().getMethods()) {
      if (m.getName().startsWith("set")) {
        dependency = findObjectByType(m.getParameterTypes()[0]);
        //이 메서드는 셋터 메서드를 호출할 때 넘겨줄 의존 객체를 찾는 일을 한다. objTable에 들어 있는 객체를 모두 뒤진다.
      //만약 셋터 메서드의 매개변수 타임과 일치하는 객체를 찾았다면 그 객체의 주소를 리턴한다.
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

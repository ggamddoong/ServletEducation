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

//프로퍼티 파일 및 애노테이션을 이용한 객체 준비
public class ApplicationContext {
 // 프로퍼티에 설정된 대로 객체를 준비하면, 객체를 저장할 보관소가 필요한데 이를 위해 해시 테이블을 준비한다.
 Hashtable<String, Object> objTable = new Hashtable<String, Object>();

 // 해시테이블에서 객체를 꺼낼 getter 메서드도 정의한다.
 public Object getBean(String key) {
     return objTable.get(key);
 }

 public void addBean(String name, Object obj) {
     objTable.put(name, obj);
 }

 // 외부에서 객체를 주입하는 경우도 고려해야 하기 때문에 일괄처리 방식을 개별처리 방식으로 변경해야 한다. 그래서 기존 생성자 제거
 /*
  * public ApplicationContext(String propertiesPath) throws Exception {
  * //Properties는 '이름=값' 형태로 된 파일을 다룰 때 사용하는 클래스이다. Properties props = new
  * Properties();
  *
  * //Properties의 load() 메서드는 FileReader를 통해 읽어들인 프로퍼티 내용을 키-값 형태로 내부 맵에
  * 보관한다. props.load(new FileReader(propertiesPath));
  *
  * prepareObjects(props); prepareAnnotationObjects(); injectDependency(); }
  */

 // 자바 classpath를 뒤져서 @Component 애노테이션이 붙은 클래스를 찾는다. 그리고 그 객체를 생성하여 객체 테이블에
 // 담는 일을 한다.
 // Reflections라는 오픈소스 라이브러리를 활용한다.
 /* private void prepareAnnotationObjects() throws Exception{ */
 public void prepareObjectsByAnnotation(String basePackage) throws Exception {
	//Reflections는 오프소스라이브러리,
	    //자바에서 제공하는 리플랙션 API보다 더 쉽게 클래스를 찾거나 클래스 정보를 추출 
	    //"" 빈문자열 매개변수는 자바 classpath에 있는 모든 패키지 검색
	    //만약 매개변수가 miniMVCFramework였다면  miniMVCFramework 패키지 및 그 하위 패키지까지 모두 뒤짐
     Reflections reflector = new Reflections(basePackage);

     
     //@Componetn 어노테이셔닝 붙은 클래스를 찾고싶으면 앞의 코드처럼 어노테이션 클래스를 지정하면 됨.
     //반환되는 값은 @Component 어노테이셔니 선언된클래스 목록임.
     Set<Class<?>> list = reflector.getTypesAnnotatedWith(Component.class);
     String key = null;
     for (Class<?> clazz : list) {
         key = clazz.getAnnotation(Component.class).value();
         objTable.put(key, clazz.newInstance());
     }
 }

 // 프로퍼티 파일의 내용을 로딩했으면 객체를 준비해야한다. prepareObjects()
 /* private void prepareObjects(Properties props) throws Exception { */
 public void prepareObjectsByProperties(String propertiesPath) throws Exception {
     Properties props = new Properties();
     props.load(new FileReader(propertiesPath));

     Context ctx = new InitialContext();
     String key = null;
     String value = null;

     for (Object item : props.keySet()) {
         key = (String) item;
         value = props.getProperty(key);
         if (key.startsWith("jndi.")) {
        	//DataSource처럼 톰캣 서버에서 제공하는 객체는 ApplicationContext에서 
       	  //생성할 수 없다. 대신 InitialContext를 통해 해당 객체를 얻어야 한다.     
        	 //InitialContext의 lookup()매서드를 이용하면, JNDI 이름으로 등록되어 있는 서버자원을 찾을 수 있습니다.
             objTable.put(key, ctx.lookup(value));
         } else {
             objTable.put(key, Class.forName(value).newInstance());
         }
     }
 }

 // 톰캣 서버로부터 객체를 가져오거나(ex: DataSource) 직접 객체를 생성했으면(ex: MemberDao), 각 객체가 필요로
 // 하는 의존 객체를 할당한다.
 public void injectDependency() throws Exception {
     for (String key : objTable.keySet()) {
         // "jndi."로 시작하는 것 제외
         if (!key.startsWith("jndi.")) {//jndi로 시작하는 경우 톰캣 서버에서 제공한 객체이므로 의존 객체 주입해서는 안된다.
             callSetter(objTable.get(key));//매개변수로 주어진 객체에 대해 셋터 메서드를 찾아서 호출하는 일
         }
     }
 }

 // 매개변수로 주어진 객체에 대해 setter 메서드를 찾아서 호출하는 일을 한다.
 private void callSetter(Object obj) throws Exception {
     Object dependency = null;
     for (Method m : obj.getClass().getMethods()) {
         // setter 메서드를 찾는다.
         if (m.getName().startsWith("set")) {
             // setter메서드의 매개변수와 타입이 일치하는 객체를 objTable에서 찾는다.
             dependency = findObjectByType(m.getParameterTypes()[0]);
             //이 메서드는 셋터 메서드를 호출할 때 넘겨줄 의존 객체를 찾는 일을 한다. objTable에 들어 있는 객체를 모두 뒤진다.
             //만약 셋터 메서드의 매개변수 타임과 일치하는 객체를 찾았다면 그 객체의 주소를 리턴한다.
             if (dependency != null) {
                 m.invoke(obj, dependency);
             }
         }
     }
 }

 // setter 메서드를 호출할 때 넘겨줄 의존 객체를 찾는 일을 한다. objTable에 들어 있는 객체를 모두 찾는다.
 private Object findObjectByType(Class<?> type) {
     for (Object obj : objTable.values()) {
         // 주어진 객체가 해당 클래스 또는 인터페이스의 인스턴스인지 찾고 그 객체의 주소를 리턴한다.
         if (type.isInstance(obj)) {
             return obj;
         }
     }
     return null;
 }
}



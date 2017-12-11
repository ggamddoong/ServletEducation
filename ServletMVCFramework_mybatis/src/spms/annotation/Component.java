package spms.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Component {
	//어노테이션 유지 정책이란 어노테이션 정보를 언제까지 유지할 것인지 설정하는 문법
	//default는 RetentionPolicy.CLASS
	//RetentionPolicy.RUNTIME로 해야 진행 중에도 언제든지 @Component 어노테이션의 속성값을 참조할 수 있다.	
  String value() default "";
}

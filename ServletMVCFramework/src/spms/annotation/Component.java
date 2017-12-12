package spms.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME) 
//������̼� ���� ��å�̶� ������̼� ������ �������� ������ ������ �����ϴ� ����
//default�� RetentionPolicy.CLASS
//RetentionPolicy.RUNTIME�� �ؾ� ���� �߿��� �������� @Component ������̼��� �Ӽ����� ������ �� �ִ�.
public @interface Component {
  String value() default "";
}

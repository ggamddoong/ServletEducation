package spms.bind;

public interface DataBinding { //프런트 컨트롤러가 페이지 컨트롤러를 실행하기 전에 원하는 데이터가 있는 경우
								//이 인터페이스를 구현
  Object[] getDataBinders();
}

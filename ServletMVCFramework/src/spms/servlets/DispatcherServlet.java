package spms.servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.bind.DataBinding;
import spms.bind.ServletRequestDataBinder;
import spms.context.ApplicationContext;
import spms.controls.Controller;
import spms.listeners.ContextLoaderListener;

// 페이지 컨트롤러를 찾을 때 ApplicationContext의 사용
@SuppressWarnings("serial")
@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {
  @Override
  protected void service(
      HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html; charset=UTF-8");
    String servletPath = request.getServletPath();
    try {
      ApplicationContext ctx = ContextLoaderListener.getApplicationContext();
      
      // 페이지 컨트롤러에게 전달할 Map 객체를 준비한다. 
      HashMap<String,Object> model = new HashMap<String,Object>();
      model.put("session", request.getSession());
      
      Controller pageController = (Controller) ctx.getBean(servletPath);
      if (pageController == null) {
        throw new Exception("요청한 서비스를 찾을 수 없습니다.");
      }
      
      if (pageController instanceof DataBinding) {
    	  //매개변수 값이 필요한 페이지 컨트롤러에 대해 DataBinding 인터페이스를 구현하기로 규칙을 정했기 때문에
    	  //DataBinding을 구현했는지 여부를 검사하여, 해당 인터페이스를 구현한 경우에만
    	  //prepareRequestData()를 호출하여 페이지 컨트롤러를  위한 데이터를 준비
        prepareRequestData(request, model, (DataBinding)pageController);
      }

      // 페이지 컨트롤러를 실행한다.
      String viewUrl = pageController.execute(model);
      
      // Map 객체에 저장된 값을 ServletRequest에 복사한다. (JSP에서 사용하기 위해)
      for (String key : model.keySet()) {
        request.setAttribute(key, model.get(key));
      }
      
      if (viewUrl.startsWith("redirect:")) {
        response.sendRedirect(viewUrl.substring(9));
        return;
      } else {
        RequestDispatcher rd = request.getRequestDispatcher(viewUrl);
        rd.include(request, response);
      }
      
    } catch (Exception e) {
      e.printStackTrace();
      request.setAttribute("error", e);
      RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
      rd.forward(request, response);
    }
  }

  private void prepareRequestData(HttpServletRequest request,
      HashMap<String, Object> model, DataBinding dataBinding)
      throws Exception {
    Object[] dataBinders = dataBinding.getDataBinders();
    String dataName = null;
    Class<?> dataType = null;
    Object dataObj = null;
    for (int i = 0; i < dataBinders.length; i+=2) {
      dataName = (String)dataBinders[i];
      dataType = (Class<?>) dataBinders[i+1];
      dataObj = ServletRequestDataBinder.bind(request, dataType, dataName);
      //bind메서드는 dataName과 일치하는 요청 매개변수(클라이언트에서 넘어온 값)를 찾고 
      //dataType을 통해 해당 클래스의 인스턴스를 생성한다.
      //찾은 매개변수 값을 인스턴스에 저장하며 그 인스턴스를 반환한다.
      model.put(dataName, dataObj);
    }
  }
}

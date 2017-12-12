package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MemberDao;
import spms.vo.Member;

// Annotation 적용
@Component("/member/add.do")
public class MemberAddController implements Controller, DataBinding {
										//클라이언트가 보낸 데이터를 프런트 컨트롤러로부터 받아야 하기 때문에 
										//DataBinding 인터페이스 구현
  MemberDao memberDao;
  
  public MemberAddController setMemberDao(MemberDao memberDao) {
    this.memberDao = memberDao;
    return this;
  }
  
  public Object[] getDataBinders() {
    return new Object[]{
        "member", spms.vo.Member.class
    };
  }
  
  @Override
  public String execute(Map<String, Object> model) throws Exception {
    Member member = (Member)model.get("member");
    if (member.getEmail() == null) { // 입력폼을 요청할 때
      return "/member/MemberForm.jsp";
    } else { // 회원 등록을 요청할 때
      memberDao.insert(member);
      return "redirect:list.do";
    }
  }
}

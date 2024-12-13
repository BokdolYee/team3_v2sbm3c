package dev.mvc.newscategrp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("dev.mvc.newscategrp.NewscategrpProc")
public class NewscategrpProc implements NewscategrpProcInter {
    @Autowired
    private NewscategrpDAOInter newscategrpDAO;

    @Override
    public int create(NewscategrpVO newscategrpVO) {
        return newscategrpDAO.create(newscategrpVO);
    }

    @Override
    public List<NewscategrpVO> list() {
        return newscategrpDAO.list();
    }

    @Override
    public NewscategrpVO read(int newscategrpno) {
        return newscategrpDAO.read(newscategrpno);
    }

    @Override
    public int update(NewscategrpVO newscategrpVO) {
        return newscategrpDAO.update(newscategrpVO);
    }

    @Override
    public int delete(int newscategrpno) {
        return newscategrpDAO.delete(newscategrpno);
    }

    @Override
    public int updateSeqno(int newscategrpno, boolean forward) {
        return newscategrpDAO.updateSeqno(newscategrpno, forward);
    }

    @Override
    public int updateVisible(int newscategrpno, String visible) {
        return newscategrpDAO.updateVisible(newscategrpno, visible);
    }
}

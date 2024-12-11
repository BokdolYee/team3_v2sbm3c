package dev.mvc.newscategrp;

import java.util.List;

public interface NewscategrpDAOInter {
    int create(NewscategrpVO newscategrpVO);

    List<NewscategrpVO> list();

    NewscategrpVO read(int newscategrpno);

    int update(NewscategrpVO newscategrpVO);

    int delete(int newscategrpno);

    int updateSeqno(int newscategrpno, boolean forward);

    int updateVisible(int newscategrpno, String visible);
}

package dev.mvc.newscate;

import java.util.ArrayList;
import java.util.Map;

import dev.mvc.newscate.NewsVO;

public interface NewsCateDAOInter {
  /** 
   * <pre>
   * MyBATIS: insert id="create" parameterType="dev.mvc.cate.CateVO">
   * insert: int를 리턴, 등록한 레코드 갯루를 리턴
   * id ="create" : 메소드명으로 사용
   * parameterType ="dev.mvc.cate.MovieVO": 메소드의 파라미터
   * Spring Boot 자동으로 구현
   *</pre>
   * @param movieVO
   * @return
   */
   public int create(NewsVO newsVO);
   
   /**
    * 전체 목록
    * SQL -> MovieVO 객체 레코드 수 만큼 생성 -> ArrayList<movieVO> 객체 생성되어 CateDAOInter로 리턴
    * select id="list_all" resultType="dev.mvc.Moviecate.MovieVO"
    * @return
    */
   public ArrayList<NewsVO> list_all();
   
   /**
    * 조회
    * @param moviecateno
    * @return
    */
   public NewsVO read(Integer newscateno);
   
   /**
    * 수정
    * @param movieVO 수정할내용
    * @return 수정된 레코드 갯수
    */
   public int update(NewsVO movieVO);
   
   /**
    * 삭제
    * @param movieVO 수정할내용
    * @return 삭제된 레코드 갯수
    */
   public int delete(int newscateno);
   
   public int deleteKorea(int newscateno);
   
   /**
    * <!--우선 순위 높임, 10 등 -> 1 등-->
    * @param moviecateno
    * @return
    */
   public int update_seqno_forward(int newscateno);
   
   /**
    *  <!--우선 순위 낮춤, 1 등 -> 10 등-->
    * @param moviecateno
    * @return
    */
   public int update_seqno_backward(int newscateno);
   
   /**
    * <!-- 카테고리 공개 설정 -->
    * @param moviecateno
    * @return
    */
   public int update_visible_y(int newscateno);
   
   /**
    * <!-- 카테고리 공개 설정 -->
    * @param moviecateno
    * @return
    */
   public int update_visible_n(int newscateno);
   
   /**
    *숨긴 카테고리 그룹을 제외하고 접속자에게 공개할 카테고리 그룹 출력
    * SQL -> MovieVO 객체 레코드 수 만큼 생성 -> ArrayList<movieVO> 객체 생성되어 CateDAOInter로 리턴
    * select id="list_all" resultType="dev.mvc.Moviecate.MovieVO"
    * @return
    */
   public ArrayList<NewsVO> list_all_categrp_y();
   
   /**
    * 숨긴 카테고리 그룹을 제외하고 접속자에게 공개할 카테고리 그룹 출력
    * SQL -> MovieVO 객체 레코드 수 만큼 생성 -> ArrayList<movieVO> 객체 생성되어 CateDAOInter로 리턴
    * select id="list_all" resultType="dev.mvc.Moviecate.MovieVO"
    * @return
    */
   public ArrayList<NewsVO> list_all_cate_y(String genre);
   
   /**
    * 장르 목록
    * @return
    */
   public ArrayList<String> genreset();
   
   public ArrayList<NewsVO> list_search(String word);
   
   /**
    * 검색 갯수
    * @param word
    * @return
    */
   public Integer list_search_count(String word);
   
   /**
    * 검색 + 페이징 목록
    * select id="list_search_paging" resultType="dev.mvc.Moviecate.MovieVO" parameterType="Map"
    * @param map
    * @return
    */
   public ArrayList<NewsVO> list_search_paging(Map<String, Object> map);
   
 }
  


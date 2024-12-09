package dev.mvc.contents;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class ContentsVO {
    /** 컨텐츠 번호 */
    private int contentno;
    /** 카테고리 번호 */
    private int cateno;
    /** 컨텐츠 비밀 번호 */
    private String passwd;
    /** 제목 */
    private String title = "";
    /** 등록 날짜 */
    private String rdate = "";
    /** 조회수 */
    private int cnt = 0;
    /** 총 조회수 */
    private int all_cnt = 0;
    /** 출력 모드 */
    private String visible;
    
    // 파일 업로드 관련
    // -----------------------------------------------------------------------------------
    /**
    이미지 파일
    <input type='file' class="form-control" name='file1MF' id='file1MF' 
               value='' placeholder="파일 선택">
    */
    private MultipartFile file1MF = null;
    /** 메인 이미지 크기 단위, 파일 크기 */
    private String size1_label = "";
    /** 메인 이미지 */
    private String file1 = "";
    /** 실제 저장된 메인 이미지 */
    private String file1saved = "";
    /** 메인 이미지 preview */
    private String thumb1 = "";
    /** 메인 이미지 크기 */
    private long size1 = 0;

}


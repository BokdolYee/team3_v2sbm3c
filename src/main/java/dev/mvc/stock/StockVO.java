package dev.mvc.stock;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class StockVO {
    /** 종목 번호 */
    private int stockno;
    /** 컨텐츠 번호 */
    private int contentno;
    /** 종목 코드 */
    private String symbol = "";
    /** 회사 이름 */
    private String name = "";
    /** 섹터 정보 */
    private String sector = "";
    /** 산업군 */
    private String industry = "";
    /** 회사 설명 */
    private String description = "";
  
}
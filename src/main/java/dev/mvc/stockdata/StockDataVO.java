package dev.mvc.stockdata;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class StockDataVO {
  
    /** 주식 데이터 번호 */
    private int sdatano;
    /** 종목 번호 */
    private int stockno;
    /** 시가 */
    private int open_price;
    /** 종가 */
    private int close_price;
    /** 거래량 */
    private int volume;
    /** 변동률 */
    private int change;
  
}
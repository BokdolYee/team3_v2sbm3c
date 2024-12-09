DROP TABLE news CASCADE CONSTRAINTS; 
DROP TABLE news;

CREATE TABLE news(
    NEWSNO                            NUMBER(10)     NOT NULL    PRIMARY KEY,
    TEXT                             VARCHAR2(3000)  NOT NULL,
    URL                              VARCHAR(255)    NULL,
    SOURCE                            VARCHAR(300)     NULL,
    PUBLISH_DATE                      DATE           NOT NULL,
    ANALYSISNO                        NUMBER(10)     NOT NULL  --FK
);

-- news 테이블의 외래 키 추가
ALTER TABLE news
ADD CONSTRAINT fk_news_analysis
FOREIGN KEY (ANALYSISNO) REFERENCES analysis (ANALYSISNO);

SELECT newsno, text, url, source, publish_date, analysisno
FROM news
ORDER BY newsno DESC;

DROP TABLE Analysis

CREATE TABLE Analysis (
	analysisno	NUMBER(10)	NOT NULL    PRIMARY KEY,
	impact	VARCHAR(300)	NULL,
	summaryno	NUMBER(10)	NOT NULL  --FK
);

-- analysis 테이블의 외래 키 추가
ALTER TABLE analysis
ADD CONSTRAINT fk_analysis_summarize
FOREIGN KEY (SUMMARYNO) REFERENCES summarize (SUMMARYNO);

SELECT analysisno, impact, summaryno
FROM analysis
ORDER BY analysisno DESC;

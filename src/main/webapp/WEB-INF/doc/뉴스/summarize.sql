DROP TABLE summarize

CREATE TABLE summarize (
	summaryno	NUMBER(10)	NOT NULL    PRIMARY KEY,
	content	VARCHAR(300)	NULL,
	newsno	Number(10)	NOT NULL --FK
);

-- summarize 테이블의 외래 키 추가
ALTER TABLE summarize
ADD CONSTRAINT fk_summarize_news_origin
FOREIGN KEY (NEWSNO) REFERENCES news (NEWSNO);

SELECT table_name FROM user_tables WHERE table_name = 'news';

SELECT summaryno, content, newsno
FROM summarize
ORDER BY summaryno DESC;

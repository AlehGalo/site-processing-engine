CREATE TABLE DATABASE_ERROR (
    DATABASE_ERROR_ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    URL VARCHAR(256) CHARACTER SET utf8 NOT NULL,
    ERROR VARCHAR(1024) CHARACTER SET utf8 NOT NULL,
    FK_JOB_ID INTEGER NOT NULL,
    FOREIGN KEY (FK_JOB_ID) REFERENCES JOB(JOB_ID)) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

CREATE TABLE CRAWLER_ERROR (
    CRAWLER_ERROR_ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    ERROR VARCHAR(1024) CHARACTER SET utf8 NOT NULL,
    FK_JOB_ID INTEGER NOT NULL,
    FOREIGN KEY (FK_JOB_ID) REFERENCES JOB(JOB_ID)) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

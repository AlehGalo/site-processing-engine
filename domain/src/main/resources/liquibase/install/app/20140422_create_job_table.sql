CREATE TABLE JOB (
    JOB_ID INTEGER PRIMARY KEY AUTO_INCREMENT,
    STOP_REASON VARCHAR(1024) CHARACTER SET utf8 NOT NULL,
    START_TIME DATETIME NOT NULL,
    END_TIME DATETIME  NOT NULL) ENGINE=INNODB DEFAULT CHARACTER SET utf8 ;

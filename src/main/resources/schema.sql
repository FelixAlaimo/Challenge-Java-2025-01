
CREATE TABLE IF NOT EXISTS SELLING_POINT (
    SEPO_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    SEPO_NAME VARCHAR(255) NOT NULL,
    SEPO_DELETED_DATE DATETIME
);

CREATE TABLE IF NOT EXISTS COST_BETWEEN_SELLING_POINTS (
    COST_FROM_SEPO_ID BIGINT NOT NULL,
    COST_TO_SEPO_ID BIGINT NOT NULL,
    COST_AMOUNT INT NOT NULL,
    PRIMARY KEY (COST_FROM_SEPO_ID, COST_TO_SEPO_ID),
    CONSTRAINT FK_COST_FROM_SEPO FOREIGN KEY (COST_FROM_SEPO_ID) REFERENCES SELLING_POINT(SEPO_ID),
    CONSTRAINT FK_COST_TO_SEPO FOREIGN KEY (COST_TO_SEPO_ID) REFERENCES SELLING_POINT(SEPO_ID)
);

CREATE TABLE IF NOT EXISTS SALE_ACCREDITATION (
    ACRE_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    ACRE_SEPO_ID BIGINT NOT NULL,
    ACRE_AMOUNT BIGINT NOT NULL,
    ACRE_DATE DATETIME NOT NULL,
    CONSTRAINT FK_ACRE_SEPO_ID FOREIGN KEY (ACRE_SEPO_ID) REFERENCES SELLING_POINT(SEPO_ID)
);
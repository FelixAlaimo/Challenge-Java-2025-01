
INSERT INTO SELLING_POINT (SEPO_ID, SEPO_NAME) SELECT 1, 'CABA' WHERE NOT EXISTS (SELECT 1 FROM SELLING_POINT LIMIT 1);
INSERT INTO SELLING_POINT (SEPO_ID, SEPO_NAME) SELECT 2, 'GBA_1' WHERE NOT EXISTS (SELECT 1 FROM SELLING_POINT LIMIT 1);
INSERT INTO SELLING_POINT (SEPO_ID, SEPO_NAME) SELECT 3, 'GBA_2' WHERE NOT EXISTS (SELECT 1 FROM SELLING_POINT LIMIT 1);
INSERT INTO SELLING_POINT (SEPO_ID, SEPO_NAME) SELECT 4, 'Santa Fe' WHERE NOT EXISTS (SELECT 1 FROM SELLING_POINT LIMIT 1);
INSERT INTO SELLING_POINT (SEPO_ID, SEPO_NAME) SELECT 5, 'Córdoba' WHERE NOT EXISTS (SELECT 1 FROM SELLING_POINT LIMIT 1);
INSERT INTO SELLING_POINT (SEPO_ID, SEPO_NAME) SELECT 6, 'Misiones' WHERE NOT EXISTS (SELECT 1 FROM SELLING_POINT LIMIT 1);
INSERT INTO SELLING_POINT (SEPO_ID, SEPO_NAME) SELECT 7, 'Salta' WHERE NOT EXISTS (SELECT 1 FROM SELLING_POINT LIMIT 1);
INSERT INTO SELLING_POINT (SEPO_ID, SEPO_NAME) SELECT 8, 'Chubut' WHERE NOT EXISTS (SELECT 1 FROM SELLING_POINT LIMIT 1);
INSERT INTO SELLING_POINT (SEPO_ID, SEPO_NAME) SELECT 9, 'Santa Cruz' WHERE NOT EXISTS (SELECT 1 FROM SELLING_POINT LIMIT 1);
INSERT INTO SELLING_POINT (SEPO_ID, SEPO_NAME) SELECT 10, 'Catamarca' WHERE NOT EXISTS (SELECT 1 FROM SELLING_POINT LIMIT 1);

INSERT INTO COST_BETWEEN_SELLING_POINTS (COST_FROM_SEPO_ID, COST_TO_SEPO_ID, COST_AMOUNT) SELECT 1, 2, 2 WHERE NOT EXISTS (SELECT 1 FROM COST_BETWEEN_SELLING_POINTS LIMIT 1);
INSERT INTO COST_BETWEEN_SELLING_POINTS (COST_FROM_SEPO_ID, COST_TO_SEPO_ID, COST_AMOUNT) SELECT 1, 3, 3 WHERE NOT EXISTS (SELECT 1 FROM COST_BETWEEN_SELLING_POINTS LIMIT 1);
INSERT INTO COST_BETWEEN_SELLING_POINTS (COST_FROM_SEPO_ID, COST_TO_SEPO_ID, COST_AMOUNT) SELECT 1, 4, 11 WHERE NOT EXISTS (SELECT 1 FROM COST_BETWEEN_SELLING_POINTS LIMIT 1);
INSERT INTO COST_BETWEEN_SELLING_POINTS (COST_FROM_SEPO_ID, COST_TO_SEPO_ID, COST_AMOUNT) SELECT 2, 3, 5 WHERE NOT EXISTS (SELECT 1 FROM COST_BETWEEN_SELLING_POINTS LIMIT 1);
INSERT INTO COST_BETWEEN_SELLING_POINTS (COST_FROM_SEPO_ID, COST_TO_SEPO_ID, COST_AMOUNT) SELECT 2, 4, 10 WHERE NOT EXISTS (SELECT 1 FROM COST_BETWEEN_SELLING_POINTS LIMIT 1);
INSERT INTO COST_BETWEEN_SELLING_POINTS (COST_FROM_SEPO_ID, COST_TO_SEPO_ID, COST_AMOUNT) SELECT 2, 5, 14 WHERE NOT EXISTS (SELECT 1 FROM COST_BETWEEN_SELLING_POINTS LIMIT 1);
INSERT INTO COST_BETWEEN_SELLING_POINTS (COST_FROM_SEPO_ID, COST_TO_SEPO_ID, COST_AMOUNT) SELECT 3, 8, 10 WHERE NOT EXISTS (SELECT 1 FROM COST_BETWEEN_SELLING_POINTS LIMIT 1);
INSERT INTO COST_BETWEEN_SELLING_POINTS (COST_FROM_SEPO_ID, COST_TO_SEPO_ID, COST_AMOUNT) SELECT 4, 5, 5 WHERE NOT EXISTS (SELECT 1 FROM COST_BETWEEN_SELLING_POINTS LIMIT 1);
INSERT INTO COST_BETWEEN_SELLING_POINTS (COST_FROM_SEPO_ID, COST_TO_SEPO_ID, COST_AMOUNT) SELECT 4, 6, 6 WHERE NOT EXISTS (SELECT 1 FROM COST_BETWEEN_SELLING_POINTS LIMIT 1);
INSERT INTO COST_BETWEEN_SELLING_POINTS (COST_FROM_SEPO_ID, COST_TO_SEPO_ID, COST_AMOUNT) SELECT 5, 8, 30 WHERE NOT EXISTS (SELECT 1 FROM COST_BETWEEN_SELLING_POINTS LIMIT 1);
INSERT INTO COST_BETWEEN_SELLING_POINTS (COST_FROM_SEPO_ID, COST_TO_SEPO_ID, COST_AMOUNT) SELECT 6, 7, 32 WHERE NOT EXISTS (SELECT 1 FROM COST_BETWEEN_SELLING_POINTS LIMIT 1);
INSERT INTO COST_BETWEEN_SELLING_POINTS (COST_FROM_SEPO_ID, COST_TO_SEPO_ID, COST_AMOUNT) SELECT 8, 9, 11 WHERE NOT EXISTS (SELECT 1 FROM COST_BETWEEN_SELLING_POINTS LIMIT 1);
INSERT INTO COST_BETWEEN_SELLING_POINTS (COST_FROM_SEPO_ID, COST_TO_SEPO_ID, COST_AMOUNT) SELECT 10, 5, 5 WHERE NOT EXISTS (SELECT 1 FROM COST_BETWEEN_SELLING_POINTS LIMIT 1);
INSERT INTO COST_BETWEEN_SELLING_POINTS (COST_FROM_SEPO_ID, COST_TO_SEPO_ID, COST_AMOUNT) SELECT 10, 7, 5 WHERE NOT EXISTS (SELECT 1 FROM COST_BETWEEN_SELLING_POINTS LIMIT 1);
CREATE TABLE insurance_policy (
              id BIGINT AUTO_INCREMENT PRIMARY KEY,
              name VARCHAR(255) NOT NULL,
              status VARCHAR(20) NOT NULL,
              coverage_start_date TIMESTAMP NOT NULL,
              coverage_end_date TIMESTAMP NOT NULL,
              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
              updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);


INSERT INTO insurance_policy (id, name, status, coverage_start_date, coverage_end_date, created_at, updated_at) VALUES
   (100, 'Tinubu Basic Plan', 'ACTIVE', '2024-01-01 00:00:00', '2025-01-01 00:00:00', '2025-01-01 00:00:00', '2025-01-01 00:00:00'),
   (101, 'Tinubu Premium Plan', 'ACTIVE', '2024-06-01 00:00:00', '2025-06-01 00:00:00', '2025-06-01 00:00:00', '2025-06-01 00:00:00'),
   (102, 'Tinubu Inactive Plan', 'INACTIVE', '2023-01-01 00:00:00', '2024-01-01 00:00:00', '2024-01-01 00:00:00', '2024-01-01 00:00:00');

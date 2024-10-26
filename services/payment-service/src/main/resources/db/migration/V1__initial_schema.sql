CREATE TABLE payment_plans (
                               payment_plan_id BIGINT PRIMARY KEY,
                               annual_cost DECIMAL(10, 2),
                               number_of_phases INT
);

CREATE TABLE payment_phases (
                                payment_phase_id BIGINT PRIMARY KEY,
                                payment_plan_id BIGINT,
                                student_id BIGINT,
                                amount_due DECIMAL(10, 2),
                                remaining_amount DECIMAL(10, 2),
                                is_paid BOOLEAN,
                                due_date DATE,
                                payment_date DATE,
                                FOREIGN KEY (payment_plan_id) REFERENCES payment_plans(payment_plan_id)
);

CREATE TABLE payments (
                          payment_id BIGINT PRIMARY KEY,
                          payment_phase_id BIGINT,
                          payment_date DATE,
                          amount DECIMAL(10, 2),
                          payment_method VARCHAR(50),
                          payment_status VARCHAR(50),
                          receipt pg_largeobject,
                          student_id BIGINT,
                          FOREIGN KEY (payment_phase_id) REFERENCES payment_phases(payment_phase_id)
);

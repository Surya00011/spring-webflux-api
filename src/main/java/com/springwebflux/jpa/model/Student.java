package com.springwebflux.jpa.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("student_details")
public record Student(
        @Id
        @Column("stu_id") Integer id,

        @Column("stu_name") String name
) {}

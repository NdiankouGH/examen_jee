package com.appexam.dao;

import com.appexam.entity.Classes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClassesRepository extends JpaRepository<Classes, Long> {
}

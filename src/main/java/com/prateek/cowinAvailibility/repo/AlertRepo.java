package com.prateek.cowinAvailibility.repo;

import java.util.List;

import com.prateek.cowinAvailibility.entity.Alerts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author prateek.mishra Repository Interface for CRUD operations on User Table
 */
public interface AlertRepo extends JpaRepository<Alerts, Integer> {

}

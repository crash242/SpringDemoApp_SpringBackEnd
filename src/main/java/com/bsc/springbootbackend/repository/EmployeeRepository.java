package com.bsc.springbootbackend.repository;

import com.bsc.springbootbackend.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE lower(e.firstName) LIKE %:query% OR lower(e.lastName) LIKE %:query%")
    List<Employee> searchEmployeesByString(@Param("query") String query);

    @Query(value = "SELECT * FROM employee WHERE lower(first_name) LIKE %:searchString% OR lower(last_name) LIKE %:searchString%", nativeQuery = true)
    List<Employee> findEmployeeBySearch(@Param("searchString") String searchString);

}

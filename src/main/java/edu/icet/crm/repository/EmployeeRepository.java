package edu.icet.crm.repository;

import edu.icet.crm.enitity.EmployeeEntity;

import org.springframework.data.repository.CrudRepository;



public interface EmployeeRepository extends CrudRepository<EmployeeEntity,Long> {

}

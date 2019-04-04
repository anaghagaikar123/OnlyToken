package com.example.demo;



public interface StudentDAO extends GenericDAO<Student,Long> {

	Student byName(String studentnm);

}

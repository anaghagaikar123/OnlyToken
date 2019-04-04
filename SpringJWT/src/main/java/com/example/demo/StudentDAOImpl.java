package com.example.demo;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;



@Repository
public class StudentDAOImpl extends GenericDAOImpl<Student,Long> implements StudentDAO{

	@Override
	public Student byName(String studentnm) {
		String hql = "from Student s where s.name =:name";
		Query query = entityManager.createQuery(hql );
		query.setParameter("name", studentnm);
		List results = query.getResultList();
		if (results.isEmpty())
			return null;
		else
			return (Student) results.get(0);
	}

	
}

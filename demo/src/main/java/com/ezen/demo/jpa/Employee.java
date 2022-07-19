package com.ezen.demo.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@Entity
@Table(name="EMP2")
@NamedStoredProcedureQueries({
	   @NamedStoredProcedureQuery(name="sp_ename_by_empno", 
	      procedureName = "ename_by_empno", 
	      parameters = {
	            @StoredProcedureParameter(mode=ParameterMode.IN, name="p_empno", type=Integer.class),
	            @StoredProcedureParameter(mode=ParameterMode.OUT, name="p_ename", type=String.class)
	      }
	   ),
	   @NamedStoredProcedureQuery(name = "sp_cur_by_empno", 
	      procedureName = "cur_by_empno",
	      resultClasses = Employee.class,
	      parameters = {
	            @StoredProcedureParameter(mode=ParameterMode.IN, type=Integer.class),
	            @StoredProcedureParameter(mode=ParameterMode.REF_CURSOR, type=void.class)
	      }
	   ),
	   @NamedStoredProcedureQuery(name = "sp_emp_by_deptno", 
	      procedureName = "emp_by_deptno",
	      resultClasses = Employee.class,
	      parameters = {
	            @StoredProcedureParameter(mode=ParameterMode.IN, type=Integer.class),
	            @StoredProcedureParameter(mode=ParameterMode.REF_CURSOR, type=void.class)
	      }
	   )
	   
})
public class Employee 
{
	@Id
   public int empno;
   public String ename;
   public java.sql.Date hiredate;
   public float sal;
   public int deptno;

   @Override
   public String toString() {
      return String.format("%d\t%s\t%s\t%f", empno,ename,hiredate,sal);
   }
   public int getEmpno() {
      return empno;
   }
   public void setEmpno(int empno) {
      this.empno = empno;
   }
   public String getEname() {
      return ename;
   }
   public void setEname(String ename) {
      this.ename = ename;
   }
   public java.sql.Date getHiredate() {
      return hiredate;
   }
   public void setHiredate(java.sql.Date hiredate) {
      this.hiredate = hiredate;
   }
   public float getSal() {
      return sal;
   }
   public void setSal(float sal) {
      this.sal = sal;
   }
	public int getDeptno() {
		return deptno;
	}
	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}
   
}
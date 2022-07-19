package com.ezen.demo.model;

import org.springframework.stereotype.Component;

@Component
public class Emp 
{
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
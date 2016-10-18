package com.iweike.dao;

public interface IwkDao {
	public boolean save(Object object);
	public boolean delete(int id,Object object);
	public boolean update(Object object);
	public Object query(int id,Object object);
}

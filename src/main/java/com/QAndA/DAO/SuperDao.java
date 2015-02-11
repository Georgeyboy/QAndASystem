package com.QAndA.DAO;

/**
 * Created by George on 11/02/2015.
 */
public interface SuperDao<T> {

	public T save(T t);
	public T get(long id);
	public T update(T t);
	public boolean delete(T t);
}

package com.QAndA.DAO;

import com.QAndA.Domain.UserRole;

/**
 * Created by George on 25/02/2015.
 */
public interface UserRoleDao extends SuperDao<UserRole> {

	public UserRole findByRole(String role);
}

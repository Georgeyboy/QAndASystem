package com.QAndA.DAO;

import com.QAndA.Domain.Notification;

/**
 * Created by George on 19/02/2015.
 */
public interface NotificationDao extends SuperDao<Notification> {

	public void setRead(Notification notification);
}

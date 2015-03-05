package com.QAndA.Exceptions;

/**
 * Created by George on 03/03/2015.
 */
public class UsernameInUseException extends Exception {

	public UsernameInUseException(){
		super("An account with this username is already registered on this system");
	}
}

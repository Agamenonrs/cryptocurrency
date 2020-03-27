/**
 * under the MIT License (MIT)
 * Copyright (c) 2015 Mercado Bitcoin Servicos Digitais Ltda.
 * @see more details in /LICENSE.txt
 */

package com.ciccc.cryptocurrency.exception;

/**
 * Mercado Bitocin generic exception type used in internal errors.
 */
public class ZBException extends Exception {

	private static final long serialVersionUID = 3299761335363609520L;

	public ZBException(String message) {
		super(message);
	}

}

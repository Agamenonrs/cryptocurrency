/**
 * under the MIT License (MIT)
 * Copyright (c) 2015 Mercado Bitcoin Servicos Digitais Ltda.
 * @see more details in /LICENSE.txt
 */

package com.ciccc.cryptocurrency.exception;

/**
 * Mercado Bitocin generic exception type used in internal errors.
 */
public class MercadoBitcoinException extends Exception {

	private static final long serialVersionUID = -6613961176558692937L;

	public MercadoBitcoinException(String message) {
		super(message);
	}

}

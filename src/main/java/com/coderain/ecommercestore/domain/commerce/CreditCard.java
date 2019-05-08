package com.coderain.ecommercestore.domain.commerce;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CreditCard {

	@NotNull
	private String cardOwner;

	@Pattern(regexp = "\\b(?:4[0-9]{12}(?:[0-9]{3})?|" + "5[12345][0-9]{14}|3[47][0-9]{13}|"
			+ "3(?:0[012345]|[68][0-9])[0-9]{11}|" + "6(?:011|5[0-9]{2})[0-9]{12}|"
			+ "(?:2131|1800|35[0-9]{3})[0-9]{11})\\b")
	private String cardNumber;
	@Pattern(regexp = "[0-9]{2}/[0-9]{2}")
	private String expiration;
	@Pattern(regexp = "[0-9] {3,4}")
	private String cvv;

	public CreditCard() {
	}

	public String getCardOwner() {
		return cardOwner;
	}

	public void setCardOwner(String cardOwner) {
		this.cardOwner = cardOwner;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getExpiration() {
		return expiration;
	}

	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

}

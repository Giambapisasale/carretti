package valueobject;

/**
 * Oggetto per il carrello
 *
 */
public final class ProdottoCarrello {
	/**
	 * prodotto scelto
	 */
	private Prodotto product;

	/**
	 * quantità desiderata per il prodotto
	 */
	private Integer quantity;

	public ProdottoCarrello(final Prodotto product) {
		this(product, 0);
	}

	public ProdottoCarrello(final Prodotto product, final Integer quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	/**
	 * @return the product
	 */
	public final Prodotto getProduct() {
		return product;
	}

	/**
	 * @return the quantity
	 */
	public final Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param product
	 *            the product to set
	 */
	public final void setProduct(Prodotto product) {
		this.product = product;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public final void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProdottoCarrello [product=" + product + ", quantity="
				+ quantity + "]";
	}

}

package de.hybris.platform.mockcarrieraddon.bean;

public class ConsignmentPojo
{
	private String consignmentCode;
	private String orderCode;
	private Double price;
	private String sku;
	private String productName;

	public String getConsignmentCode()
	{
		return consignmentCode;
	}

	public void setConsignmentCode(final String consignmentCode)
	{
		this.consignmentCode = consignmentCode;
	}

	public String getOrderCode()
	{
		return orderCode;
	}

	public void setOrderCode(final String orderCode)
	{
		this.orderCode = orderCode;
	}

	public Double getPrice()
	{
		return price;
	}

	public void setPrice(final Double price)
	{
		this.price = price;
	}

	public String getSku()
	{
		return sku;
	}

	public void setSku(final String sku)
	{
		this.sku = sku;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(final String productName)
	{
		this.productName = productName;
	}
}

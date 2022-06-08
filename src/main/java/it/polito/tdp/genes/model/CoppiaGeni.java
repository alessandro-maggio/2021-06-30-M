package it.polito.tdp.genes.model;

public class CoppiaGeni {
	
	private String gId1;
	private String gId2;
	private double peso;
	
	
	public CoppiaGeni(String gId1, String gId2, double peso) {
		super();
		this.gId1 = gId1;
		this.gId2 = gId2;
		this.peso = peso;
	}


	public String getgId1() {
		return gId1;
	}


	public void setgId1(String gId1) {
		this.gId1 = gId1;
	}


	public String getgId2() {
		return gId2;
	}


	public void setgId2(String gId2) {
		this.gId2 = gId2;
	}


	public double getPeso() {
		return peso;
	}


	public void setPeso(double peso) {
		this.peso = peso;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gId1 == null) ? 0 : gId1.hashCode());
		result = prime * result + ((gId2 == null) ? 0 : gId2.hashCode());
		long temp;
		temp = Double.doubleToLongBits(peso);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoppiaGeni other = (CoppiaGeni) obj;
		if (gId1 == null) {
			if (other.gId1 != null)
				return false;
		} else if (!gId1.equals(other.gId1))
			return false;
		if (gId2 == null) {
			if (other.gId2 != null)
				return false;
		} else if (!gId2.equals(other.gId2))
			return false;
		if (Double.doubleToLongBits(peso) != Double.doubleToLongBits(other.peso))
			return false;
		return true;
	}
	
	
	
	

}

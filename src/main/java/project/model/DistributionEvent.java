package project.model;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class DistributionEvent implements Serializable{
	private static final long serialVersionUID = 1L;
	private AidItem item;
	private Beneficiary beneficiary;
	private GregorianCalendar date;

	public DistributionEvent(AidItem item, Beneficiary beneficiary, GregorianCalendar date) {
		this.item = item;
		this.beneficiary = beneficiary;
		this.date = date;
	}

	public AidItem getItem() {
		return item;
	}

	public Beneficiary getBeneficiary() {
		return beneficiary;
	}

	public GregorianCalendar getDate() {
		return date;
	}
	//  بنحط اول شي التوسترينق لل beneficiary وباعدها الاياتم وفي التاريخ نحط الايام والشهر والسنة
	@Override
	public String toString() {
		return beneficiary.toString() + " received " + item.toString() + " on " +
				date.get(GregorianCalendar.DAY_OF_MONTH) + "/" + (date.get(GregorianCalendar.MONTH) + 1) + "/" +date.get(GregorianCalendar.YEAR);
		// بنحط +1 عند الشهر عشانو ببلش من صفر
	}



}

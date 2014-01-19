package to;

import java.util.ArrayList;
import java.util.List;

public class Solution {
	private List<Pallet> pallets;
	
	public Solution() {
		pallets = new ArrayList<>();
	}
	
	public float getPalletsFootprintsSum() {
		float sum = 0;
		for (Pallet pallet : pallets) {
			sum += pallet.getFootprint();
		}
		return sum;
	}

	public void addPallet(Pallet pallet) {
		pallets.add(pallet);
	}
	
	public Pallet getPalletFor(Package package0) {
		for (Pallet pallet : pallets) {
			if (pallet.canContainPackage(package0)) {
				return pallet;
			}
		}
		
		return null;
	}
}
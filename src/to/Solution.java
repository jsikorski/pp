package to;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Solution implements Comparable<Solution> {	
	private List<Pallet> pallets;
	
	public Solution() {
		pallets = new ArrayList<>();
	}
	
	public Solution(List<Pallet> pallets) {
		this.pallets = pallets;
	}
	
	public Pallet getPalletFor(Package package0) {
		for (Pallet pallet : pallets) {
			if (pallet.canContainPackage(package0)) {
				return pallet;
			}
		}
		return null;
	}

	public void addPallet(Pallet pallet) {
		pallets.add(pallet);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		String newLine = System.lineSeparator();
		
		builder.append("1" + newLine);
		builder.append(getPalletsFootprintsSum() + "\t" + getPalletsSmallestVolume() + newLine);
		
		builder.append(pallets.size() + newLine);
		for (Pallet pallet : pallets) {
			builder.append(pallet.toString() + newLine);
		}
		
		// To get packages in correct order
		List<Tuple<String, String>> packagesMap = getPackagesMap();
		builder.append(packagesMap.size());
		for (Tuple<String, String> tuple: packagesMap) {
			builder.append(newLine);
			builder.append(tuple.getFirst() + "\t" + tuple.getSecond());
		}
		
		return builder.toString();
	}
	
	public float getPalletsFootprintsSum() {
		float sum = 0;
		for (Pallet pallet : pallets) {
			sum += pallet.getFootprint();
		}
		return sum;
	}

	public float getPalletsSmallestVolume() {
		Pallet result = null;
		for (Pallet pallet : pallets) {
			if (result == null || pallet.getVolume() < result.getVolume()) {
				result = pallet;
			}
		}
		return result != null ? result.getVolume() : 0;
	}
	
	private List<Tuple<String, String>> getPackagesMap() {
		List<Tuple<String, String>> packagesMap = new ArrayList<>();
		for (Pallet pallet : pallets) {
			for (Package package0 : pallet.getPackages()) {
				packagesMap.add(new Tuple<String, String>(package0.getId(), pallet.getId()));
			}
		}
		Collections.sort(packagesMap, new Comparator<Tuple<String, String>>() {
			@Override
			public int compare(Tuple<String, String> o1, Tuple<String, String> o2) {
				int firstIdNumber = Integer.parseInt(o1.getFirst().substring(1));
				int secondIdNumber = Integer.parseInt(o2.getFirst().substring(1));
				return Integer.compare(firstIdNumber, secondIdNumber);
			}
		});
		return packagesMap;
	}

	@Override
	public int compareTo(Solution otherSolution) {
		float footprintsSum = getPalletsFootprintsSum();
		float otherFootprintsSum = otherSolution.getPalletsFootprintsSum();
		
		if (footprintsSum < otherFootprintsSum) {
			return 1;
		} else if (footprintsSum > otherFootprintsSum) {
			return -1;
		}
		
		float smallestVolume = getPalletsSmallestVolume();
		float otherSmallestVolume = otherSolution.getPalletsSmallestVolume();
		
		if (smallestVolume < otherSmallestVolume) {
			return 1;
		} else if (smallestVolume > otherSmallestVolume) {
			return -1;
		}
		
		return 0;
	}
	
	public boolean isBetterThan(Solution solution) {
		return compareTo(solution) == 1;
	}

	public List<Pallet> clonePallets() {
		List<Pallet> copy = new ArrayList<>(pallets.size());
		for (Pallet pallet : pallets) {
			copy.add(new Pallet(pallet));
		}
		return copy;
	}

	public int getTotalNumberOfPackages() {
		int sum = 0;
		for (Pallet pallet : pallets) {
			sum += pallet.getNumberOfPackages();
		}
		return sum;
	}
}
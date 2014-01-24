package to;

import java.util.Comparator;

final class PackagesByWeightsDesc implements Comparator<Package> {
	@Override
	public int compare(Package o1, Package o2) {
		return -Float.compare(o1.getWeight(), o2.getWeight());
	}
}
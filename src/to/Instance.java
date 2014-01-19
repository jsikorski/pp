package to;

import java.util.Collections;
import java.util.List;

public class Instance {

	private List<PalletType> palletTypes;
	private List<Package> packages;

	public Instance(List<PalletType> palletTypes, List<Package> packages) {
		this.palletTypes = palletTypes;
		this.packages = packages;
	}

	public List<PalletType> getPalletTypes() {
		return palletTypes;
	}

	public List<Package> getPackages() {
		return packages;
	}
	
	public void shufflePackages() {
		Collections.shuffle(packages);
	}
	
	public PalletType getPalletTypeById(String palletId) {
		for (PalletType palletType : palletTypes) {
			if (palletType.getId().equals(palletId)) {
				return palletType;
			}
		}
		return null;
	}
}

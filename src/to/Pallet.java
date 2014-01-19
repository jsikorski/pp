package to;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pallet {

	private PalletType palletType;
	
	private final String id;
	private final List<Package> packages;
	
	public Pallet(String id, PalletType palletType) {
		this.id = id;
		this.packages = new ArrayList<>();
		this.palletType = palletType;
	}
	
	private Pallet(List<Package> packages) {
		this.id = "";
		this.packages = packages;
		this.palletType = getPalletTypeWithLargestVolume();
	}

	public void addPackage(Package package0) {
		packages.add(package0);
		palletType = getPalletTypeWithLargestVolume();
	}
	
	private PalletType getPalletTypeWithLargestVolume() {
		PalletType result = null;
		float largestVolume = Float.MIN_VALUE;
		Map<PalletType, Float> volumesPerPalletType = new HashMap<>();
		
		for (Package package0 : packages) {
			PalletType prefferedPalletType = package0.getPrefferedPalletType();
			if (!volumesPerPalletType.containsKey(prefferedPalletType)) {
				volumesPerPalletType.put(prefferedPalletType, 0.0f);
			}
			float volume = volumesPerPalletType.get(prefferedPalletType) + package0.getVolume();
			volumesPerPalletType.put(prefferedPalletType, volume);
			if (volume > largestVolume) {
				result = prefferedPalletType;
				largestVolume = volume;
			}
		}
		
		return result;
	}

	public float getFootprint() {
		return palletType.getFootprint();
	}
	
	public boolean canContainPackage(Package package0) {
		List<Package> newPackages = new ArrayList<>(packages);
		newPackages.add(package0);
		return new Pallet(newPackages).isValid();
	}
	
	private boolean isValid() {
		return getNumberOfRaiserModules() <= palletType.getMaxNumerOfRaiserModules() 
			&& getWeightOfContainedPackages() <= palletType.getMaxPackagesWeight(); 		
	}
	
	private int getNumberOfRaiserModules() {
		float packagesVolume = 0;
		for (Package package0 : packages) {
			packagesVolume += package0.getVolume();
		}
		float height = packagesVolume / getFootprint();
		return (int) Math.ceil(height / palletType.getRaiserModuleHeight());
	}

	private float getWeightOfContainedPackages() {
		float sum = 0;
		for (Package package0 : packages) {
			sum += package0.getWeight();
		}
		return sum;
	}
	
	public String getId() {
		return id;
	}
	
	public float getVolume() {
		return getNumberOfRaiserModules() + palletType.getRaiserModuleHeight();
	}

	public List<Package> getPackages() {
		return packages;
	}
	
	@Override
	public String toString() {
		return getId() + "\t" + palletType.getId() + "\t" + getNumberOfRaiserModules();
	}
}

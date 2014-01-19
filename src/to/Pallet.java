package to;

import java.util.ArrayList;
import java.util.List;

public class Pallet {

	private final PalletType palletType;
	private final int maxNumberOfRaiserModules;
	
	private final String id;
	private final List<Package> packages;
	private int numberOfRaiserModules; 
	
	public Pallet(String id, PalletType palletType) {
		this.id = id;
		this.palletType = palletType;
		packages = new ArrayList<>();
		maxNumberOfRaiserModules = (int) Math.floor(
			palletType.getMaxRaiserHeight() / palletType.getRaiserModuleHeight());
	}

	public void addPackage(Package package0) {
		numberOfRaiserModules = getNumberOfRaiserModulesRequiredToContain(package0);
		packages.add(package0);
	}
	
	private int getNumberOfRaiserModulesRequiredToContain(Package package0) {
		float newHeight = getHeight() + (package0.getVolume() / getFootprint());
		return (int) Math.ceil(newHeight / palletType.getRaiserModuleHeight());
	}
	
	public float getFootprint() {
		return palletType.getFootprint();
	}
	
	private float getHeight() {
		return getNumberOfRaiserModules() * palletType.getRaiserModuleHeight();
	}
	
	private int getNumberOfRaiserModules() {
		return numberOfRaiserModules;
	}
	
	public boolean canContainPackage(Package package0) {
		return getNumberOfRaiserModulesRequiredToContain(package0) <= maxNumberOfRaiserModules 
			&& getWeightOfContainedPackages() + package0.getWeight() <= palletType.getMaxPackagesWeight();
	}
	
	private float getWeightOfContainedPackages() {
		float sum = 0;
		for (Package package0 : packages) {
			sum += package0.getWeight();
		}
		return sum;
	}
	
	@Override
	public String toString() {
		return id + "\t" + palletType.getId() + "\t" + numberOfRaiserModules;
	}
}

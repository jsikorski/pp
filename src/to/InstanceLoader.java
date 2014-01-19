package to;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InstanceLoader {
	private String filePath;

	public InstanceLoader(String filePath) {
		this.filePath = filePath;
	}
	
	public Instance load() throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		List<PalletType> palletTypes = readPalletsSection(reader);
		List<Package> packages = readPackagesSection(reader, palletTypes);
		return new Instance(palletTypes, packages);
	}
	
	private List<PalletType> readPalletsSection(BufferedReader reader) throws IOException {
		int numberOfLines = Integer.parseInt(reader.readLine());
		List<PalletType> palletTypes = new ArrayList<>(numberOfLines);
		
		for (int i = 0; i < numberOfLines; i++) {
			String line = reader.readLine();
			String[] parts = line.split("\t");
			palletTypes.add(
				new PalletType(
					parts[0], 
					Float.parseFloat(parts[1]), 
					Float.parseFloat(parts[2]), 
					Float.parseFloat(parts[3]),
					Float.parseFloat(parts[4])
				)
			);
		}
		
		return palletTypes;
	}
	
	private List<Package> readPackagesSection(BufferedReader reader, List<PalletType> palletTypes) throws Exception {
		int numberOfLines = Integer.parseInt(reader.readLine());
		List<Package> packages = new ArrayList<>(numberOfLines);
		
		for (int i = 0; i < numberOfLines; i++) {
			String line = reader.readLine();
			String[] parts = line.split("\t");
			packages.add(
				new Package(
					parts[0], 
					Float.parseFloat(parts[1]), 
					Float.parseFloat(parts[2]),
					getPalletTypeById(palletTypes, parts[3])
				)
			);
		}
		
		return packages;
	}
	
	public PalletType getPalletTypeById(List<PalletType> palletTypes, String palletTypeId) throws Exception {
		for (PalletType palletType : palletTypes) {
			if (palletType.getId().equals(palletTypeId)) {
				return palletType;
			}
		}
		
		throw new Exception("Cannot find pallet type with id " + palletTypeId);
	}
}

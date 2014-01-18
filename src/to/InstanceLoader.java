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
	
	public Instance load() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		List<PalletType> palletTypes = readPalletsSection(reader);
		List<Package> packages = readPackagesSection(reader);
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
					Float.parseFloat(parts[3])
				)
			);
		}
		
		return palletTypes;
	}
	
	private List<Package> readPackagesSection(BufferedReader reader) throws IOException {
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
					parts[3]
				)
			);
		}
		
		return packages;
	}
}

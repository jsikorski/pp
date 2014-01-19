package to;

public class Package {
	private String id;
	private float volume;
	private float weight;
	private PalletType prefferedPalletType;
	
	public Package(String id, float volume, float weight, PalletType prefferedPalletType) {
		this.id = id;
		this.volume = volume;
		this.weight = weight;
		this.prefferedPalletType = prefferedPalletType;
	}
	
	public String getId() {
		return id;
	}
	
	public float getVolume() {
		return volume;
	}
	
	public float getWeight() {
		return weight;
	}
	
	public PalletType getPrefferedPalletType() {
		return prefferedPalletType;
	}
}
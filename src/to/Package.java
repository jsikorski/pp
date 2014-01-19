package to;

public class Package {
	private String id;
	private float volume;
	private float weight;
	private String prefferedPalletId;
	
	public Package(String id, float volume, float weight, String prefferedPalletId) {
		this.id = id;
		this.volume = volume;
		this.weight = weight;
		this.prefferedPalletId = prefferedPalletId;
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
	
	public String getPrefferedPalletId() {
		return prefferedPalletId;
	}
}

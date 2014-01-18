package to;

public class Package {
	private String id;
	private float volume;
	private float weight;
	private String prefferedPallet;
	
	public Package(String id, float volume, float weight, String prefferedPallet) {
		this.id = id;
		this.volume = volume;
		this.weight = weight;
		this.prefferedPallet = prefferedPallet;
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
	
	public String getPrefferedPallet() {
		return prefferedPallet;
	}
}

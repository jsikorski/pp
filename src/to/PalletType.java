package to;

public class PalletType {
	private String id;
	private float footprint;
	private float maxRaiserHeight;
	private float maxWeight;
	
	public PalletType(String id, float footprint, float maxRaiserHeight, float maxWeight) {
		this.id = id;
		this.footprint = footprint;
		this.maxRaiserHeight = maxRaiserHeight;
		this.maxWeight = maxWeight;
	}
	
	public String getId() {
		return id;
	}
	
	public float getFootprint() {
		return footprint;
	}
		
	public float getMaxRaiserHeight() {
		return maxRaiserHeight;
	}
		
	public float getMaxWeight() {
		return maxWeight;
	}
}

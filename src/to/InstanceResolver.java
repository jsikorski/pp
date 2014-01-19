package to;

public class InstanceResolver {
	private Instance instance;
	private int lastPalletId;
	
	public InstanceResolver(Instance instance) {
		this.instance = instance;
	}
	
	public Solution findSolution() {
		Solution solution = new Solution();
		
		for (Package package0 : instance.getPackages()) {
			Pallet pallet = solution.getPalletFor(package0);
			if (pallet == null) {
				pallet = new Pallet(generatePalletId(), package0.getPrefferedPalletType());
				solution.addPallet(pallet);
			}
			
			pallet.addPackage(package0);
		}
		
		return solution;
	}
	
	private String generatePalletId() {
		return "n" + ++lastPalletId;
	}
}
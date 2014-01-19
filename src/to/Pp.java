package to;

public class Pp {

	private static final int NUMBER_OF_RUNS = 11;

	public static void main(String[] args) throws Exception {
		
		float avgPalletsFootprintsSum = 0;
		float getPalletsSmallestVolume = 0;
		
		for (int i = 0; i < NUMBER_OF_RUNS; i++) {
			long start = System.currentTimeMillis();
			
			InstanceLoader instanceLoader = new InstanceLoader("data/pp101.in");
			Instance instance = instanceLoader.load();
			instance.shufflePackages();
			
			InstanceResolver resolver = new InstanceResolver(instance);
			Solution solution = resolver.findSolution();
			
			avgPalletsFootprintsSum += solution.getPalletsFootprintsSum();
			getPalletsSmallestVolume += solution.getPalletsSmallestVolume();
			
			System.out.println(solution);
			System.out.println();
			
			long duration = System.currentTimeMillis() - start;
			if (duration > 1000) {
				throw new Exception("Duration time longer then 1 second");
			}			
		}
		
		avgPalletsFootprintsSum /= NUMBER_OF_RUNS;
		getPalletsSmallestVolume /= NUMBER_OF_RUNS;
		System.out.println("Average solution: " + avgPalletsFootprintsSum + " " + getPalletsSmallestVolume);
	}
}
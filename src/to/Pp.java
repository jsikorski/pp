package to;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pp {

	private static final int NUMBER_OF_RUNS = 11;

	public static void main(String[] args) throws Exception {

		float avgPalletsFootprintsSum = 0;
		float getPalletsSmallestVolume = 0;
		List<List<Float>> footprints = new ArrayList<List<Float>>();
		List<List<Float>> smallestVolumes = new ArrayList<List<Float>>();
		
		for (int i = 0; i < 10; i++) {
			footprints.add(new ArrayList<Float>());
			smallestVolumes.add(new ArrayList<Float>());
			for (int j = 0; j < NUMBER_OF_RUNS; j++) {
				long start = System.currentTimeMillis();

				String path = i < 9 ? "data/pp10" + (i + 1) + ".in"
						: "data/pp1" + (i + 1) + ".in";
				InstanceLoader instanceLoader = new InstanceLoader(path);
				Instance instance = instanceLoader.load();
				instance.sortPackages(new PackagesByWeightsDesc());

				InstanceResolver resolver = new InstanceResolver(instance);
				Solution solution = resolver.findSolution();
				
				SolutionOptimizer solutionOptimizer = new LocalSearchSolutionOptimizer(solution, 750);
				solution = solutionOptimizer.optimize();

				avgPalletsFootprintsSum += solution.getPalletsFootprintsSum();
				getPalletsSmallestVolume += solution.getPalletsSmallestVolume();
				footprints.get(i).add(solution.getPalletsFootprintsSum());
				smallestVolumes.get(i).add(solution.getPalletsSmallestVolume());

				long duration = System.currentTimeMillis() - start;
				if (duration > 1000) {
					throw new Exception("Duration time longer then 1 second");
				}
				
				if (instance.getPackages().size() != solution.getTotalNumberOfPackages()) {
					throw new Exception(
						"Invalid solution." + System.lineSeparator() +
						"Number of packages in instance: " + instance.getPackages().size() + System.lineSeparator() + 
						"Number of packages in solution: " + solution.getTotalNumberOfPackages()
					);
				}
			}
		}

		avgPalletsFootprintsSum /= NUMBER_OF_RUNS * 10;
		getPalletsSmallestVolume /= NUMBER_OF_RUNS * 10;
		System.out.println("Average solution: " + avgPalletsFootprintsSum + " "
				+ getPalletsSmallestVolume);
		// Collections.sort(footprints);
		for(int i = 0; i < footprints.size(); i++) {
			System.out.println("Instance " + (i + 1) + ":");
			//System.out.println("\t" + footprints.get(i));
			Collections.sort(footprints.get(i));
			Collections.sort(smallestVolumes.get(i));
			float avgFootprint = 0.0f;
			float avgSmallestVolume = 0.0f;
			for(int j = 0; j < footprints.get(i).size(); j++) {
				avgFootprint += footprints.get(i).get(j);
				avgSmallestVolume += smallestVolumes.get(i).get(j);
			}
			avgFootprint /= footprints.get(i).size();
			avgSmallestVolume /= smallestVolumes.get(i).size();
			
			System.out.println("\tFootprints:");
			System.out.println("\t\tMinimum solution: " + footprints.get(i).get(0));
			System.out.println("\t\tMaximum solution: "
					+ footprints.get(i).get(footprints.get(i).size() - 1));
			System.out.println("\t\tMedian solution: "
					+ footprints.get(i).get(footprints.get(i).size() / 2));
			System.out.println("\t\tAverage solution: " + avgFootprint);
			
			System.out.println("\tSmallestVolumes:");
			System.out.println("\t\tMinimum solution: " + smallestVolumes.get(i).get(0));
			System.out.println("\t\tMaximum solution: "
					+ smallestVolumes.get(i).get(smallestVolumes.get(i).size() - 1));
			System.out.println("\t\tMedian solution: "
					+ smallestVolumes.get(i).get(smallestVolumes.get(i).size() / 2));
			System.out.println("\t\tAverage solution: " + avgSmallestVolume);
		}
	}
}
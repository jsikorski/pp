package to;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LocalSearchSolutionOptimizer implements SolutionOptimizer {

	private static final int NUMBER_OF_THREADS = 4;
	private static final int MAX_NUMBER_OF_CHANGED_IN_STEP_PACKAGES = 8;
	
	private Random random;
	private Solution bestSolution;
	private boolean shouldTerminate;
	private PackagesByWeightsDesc packagesComparator;
	private int timeout;

	public LocalSearchSolutionOptimizer(Solution solution, int timeout) {
		random = new Random();
		bestSolution = solution;
		packagesComparator = new PackagesByWeightsDesc();
		this.timeout = timeout;
	}
	
	public Solution optimize() {
		ExecutorService pool = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
		
		List<Callable<Solution>> tasks = new ArrayList<>(NUMBER_OF_THREADS);
		for (int i = 0; i < NUMBER_OF_THREADS; i++) {
			tasks.add(new FindBetterSolution());
		}
		
		try {
			pool.invokeAll(tasks, timeout, TimeUnit.MILLISECONDS);
			shouldTerminate = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pool.shutdown();
		}
		
		return bestSolution;
	}
	
	
	private final class FindBetterSolution implements Callable<Solution> {

		@Override
		public Solution call() throws Exception {
			for (int stepNumber = 0; true; stepNumber++) {
				if (shouldTerminate) {
					return bestSolution;
				}
				
				Solution s = bestSolution;
				
				for (int i = 0; i < 3 + (int) Math.max((10000 - stepNumber) / 1000, 0); i++) {
					s = invokeStep(s);
					updateBestSolution(s);
				}
				
				updateBestSolution(s);
			}
		}
		
		private synchronized void updateBestSolution(Solution newSolution) {
			if (newSolution.isBetterThan(bestSolution)) {
				bestSolution = newSolution;
			}
		}
		
		private Solution invokeStep(Solution solution) {
			List<Pallet> pallets = solution.clonePallets();
			
			int palletIndex = 0;
			Pallet firstPallet = null;
			for (int i = 0; i < pallets.size(); i++) {
				Pallet pallet = pallets.get(i);
				if (pallet.getNumberOfPackages() == 1) {
					firstPallet = pallet;
					palletIndex = i;
				}
			}
			
			if (firstPallet != null) {
				Package removedPackage = removeRandomPackageFrom(firstPallet);
				if (firstPallet.isEmpty()) {
					pallets.remove(firstPallet);
				}
				
				for (int i = 0; i < pallets.size(); i++) {
					if (i == palletIndex) {
						continue;
					}
					
					if (pallets.get(i).addIfCanContain(removedPackage)) {
						return new Solution(pallets);
					}
				}
			}
			
			pallets = solution.clonePallets();
			
			int numberOfPalletsToRemove = random.nextInt(MAX_NUMBER_OF_CHANGED_IN_STEP_PACKAGES);
			List<Tuple<Package, Integer>> removedPackages = new ArrayList<>(numberOfPalletsToRemove);
			for (int removed = 0; removed < numberOfPalletsToRemove; removed++) {
				palletIndex = random.nextInt(pallets.size());
				Pallet pallet = pallets.get(palletIndex);
				removedPackages.add(
					new Tuple<Package, Integer>(
						removeRandomPackageFrom(pallet), 
						palletIndex)
					);			
			}
			
			Collections.sort(removedPackages, new Comparator<Tuple<Package,Integer>>() {
				@Override
				public int compare(Tuple<Package, Integer> t1, Tuple<Package, Integer> t2) {
					return packagesComparator.compare(t1.getFirst(), t2.getFirst());
				}
			});
			
			int numberOfInsertedPackages = 0;
			for (Tuple<Package, Integer> removedPackage : removedPackages) {
				for (int i = 0; i < pallets.size(); i++) {
					if (i == removedPackage.getSecond()) {
						continue;
					}
					
					if (pallets.get(i).addIfCanContain(removedPackage.getFirst())) {
						numberOfInsertedPackages++;
						break;
					}
				}
			}
			
			if (numberOfInsertedPackages != removedPackages.size()) {
				return solution;
			}
			
			List<Pallet> newPallets = new ArrayList<>();
			for (Pallet pallet : pallets) {
				if (!pallet.isEmpty()) {
					newPallets.add(pallet);
				}
			}
			
			return new Solution(newPallets);
		}
		
		private Package removeRandomPackageFrom(Pallet pallet) {
			int removedPackageIndex = random.nextInt(pallet.getPackages().size());
			Package removedPackage = pallet.removePackageAt(removedPackageIndex);			
			return removedPackage;
		}
	}
}
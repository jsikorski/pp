package to;

public class Pp {

	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();
				
		InstanceLoader instanceLoader = new InstanceLoader("data/pp101.in");
		Instance instance = instanceLoader.load();
		instance.shufflePackages();
		
		InstanceResolver resolver = new InstanceResolver(instance);
		Solution solution = resolver.findSolution();
		
		System.out.println(solution);
		System.out.println();
		System.out.println(System.currentTimeMillis() - start);
	}
}
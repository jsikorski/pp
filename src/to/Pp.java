package to;

public class Pp {

	public static void main(String[] args) throws Exception {
		InstanceLoader instanceLoader = new InstanceLoader("data/pp103.in");
		Instance instance = instanceLoader.load();
		//instance.shufflePackages();
		
		InstanceResolver resolver = new InstanceResolver(instance);
		Solution solution = resolver.findSolution();
		
		System.out.println(solution);
	}

}

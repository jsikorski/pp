package to;

import java.io.IOException;

public class Pp {

	public static void main(String[] args) throws IOException {
		InstanceLoader instanceLoader = new InstanceLoader("data/pp101.in");
		Instance instance = instanceLoader.load();
	}

}

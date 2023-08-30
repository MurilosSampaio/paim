
public class PointwiseTransform extends Object {

	/**
	* Question 1.1 Contrast reversal
	*/
	static public ImageAccess inverse(ImageAccess input) {
		int nx = input.getWidth();
		int ny = input.getHeight();
		ImageAccess output = new ImageAccess(nx, ny);
		double value = 0.0;
		for (int x=0; x<nx; x++)
		for (int y=0; y<ny; y++) {
			value = input.getPixel(x, y);
			value = 255 - value;
			output.putPixel(x, y, value);
		}
		return output;	
	}

	/**
	* Question 1.2 Stretch normalized constrast
	*/
	static public ImageAccess rescale(ImageAccess input) {
		int nx = input.getWidth();
		int ny = input.getHeight();
		double max = input.getMaximum();
		double min = input.getMinimum();
		ImageAccess output = new ImageAccess(nx, ny);

		//Declarar valores 
	    double alpha = 255.0 / (max - min);
	    double beta = min;
	    double value = 0.0;

	    for (int x=0; x<nx; x++){
		for (int y=0; y<ny; y++) {
			value = alpha * (input.getPixel(x, y) - beta);
			output.putPixel(x, y, value);
		}
		}
		return output;	
	}

	/**
	* Question 1.3 Saturate an image
	*/
	static public ImageAccess saturate(ImageAccess input) {
		int nx = input.getWidth();
		int ny = input.getHeight();
		ImageAccess output = new ImageAccess(nx, ny);
		for (int x=0; x<nx; x++)
		for (int y=0; y<ny; y++) {
			if (input.getPixel(x, y) > 10000) {
				output.putPixel(x, y, 10000);
			} else {
				output.putPixel(x, y, input.getPixel(x, y));
			}
		}
		return output;
	}
	
	/**
	* Question 3.1 Maximum Intensity Projection
	*/
	static public ImageAccess zprojectMaximum(ImageAccess[] zstack) {
		int nx = zstack[0].getWidth();
		int ny = zstack[0].getHeight();
		int nz = zstack.length;
		ImageAccess output = new ImageAccess(nx, ny);
    	
    	double value_max = 0.0;
    	for (int x=0; x<nx; x++)
		for (int y=0; y<ny; y++) {
    	  	value_max = zstack[0].getPixel(x, y);
    	  	for (int z=0; z<nz; z++){
				if (value_max < zstack[z].getPixel(x, y)) {
    	      	value_max = zstack[z].getPixel(x, y);
				}
    	  	}

			output.putPixel(x, y, value_max);
    	}
		return output;	
	}

	/**
	* Question 3.2 Z-stack mean
	*/
	static public ImageAccess zprojectMean(ImageAccess[] zstack) {
		int nx = zstack[0].getWidth();
		int ny = zstack[0].getHeight();
		int nz = zstack.length;
		ImageAccess output = new ImageAccess(nx, ny);
    	double z_accumulator = 0.0;
    	for (int x=0; x<nx; x++){
		for (int y=0; y<ny; y++) {
			z_accumulator = 0.0;
    		for (int z=0; z<nz; z++){
    	    	z_accumulator += zstack[z].getPixel(x, y); 
		  	}
			output.putPixel(x, y, z_accumulator/nz);
    	}
		}
		return output;	
	}

}

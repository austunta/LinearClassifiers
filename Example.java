import java.util.Arrays;
/*
 * an example consists of an array of values associated with example's features
 * and a result value
 * by default, an example is initiated to a value array whose size is a user inputted number of features
 * and a result value of 0.0
 *
 */
public class Example {

	public double inputData[];
	public double result;

	public Example(double inputData[], double result) {
		this.inputData = inputData;
		this.result = result;
	}

	public Example(int inputNo) {
		inputData = new double[inputNo];
		result = 0.0;
	}

	public String toString() {
		return Arrays.toString(inputData) + " : " + result;
	}

}

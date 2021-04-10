import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class LinearClassifier {

    double weightVector[];
    List<Double> trainingReport = new LinkedList<Double>();

    // abstracts methods to be implemented by PerceptronClassifier and Logistic Classifier
    public abstract double threshold(double point);

    public abstract double accuracy(List<Example> examples);

    public abstract void update(double weightVector[], Example ex, double alpha);


    //non abstract method to be overridden
    public void train(int steps, List<Example> examples) {
    }


    // initialize weight vector to a random point in the parameter space
    public void initWeightVector() {
        Random random = new Random();
        int size = weightVector.length;
        for (int i = 0; i < size; i++) {
            weightVector[i] = random.nextDouble();
        }
    }

    public void initTrainingReport() {
        trainingReport.clear();
        trainingReport.add(0, 0.0);
    }

    public LinearClassifier(int inputNo) {
        this.weightVector = new double[inputNo];
        initWeightVector();
        initTrainingReport();
    }

    // vector dot product
    public double dotProduct(double weightVector[], double inputVector[]) {
        double result = 0;
        int wsize = weightVector.length;
        int isize = inputVector.length;
        if (wsize != isize) {
            System.out.println("LENGTH MISMATCH: vectors sizes are not same w:" + wsize + " x:" + isize);
            return 0;
        }
        for (int i = 0; i < wsize; i++) {
            result += weightVector[i] * inputVector[i];
        }
        return result;

    }

    // hw(x): hypothesis function
    public double hypothesis(double[] inputVector) {
        return threshold(dotProduct(weightVector, inputVector));
    }

    // squared error
    public double L2error(List<Example> exampleList) {
        double errorsum = 0;
        double error = 0;
        int size = exampleList.size();

        for (Example ex : exampleList) {
            error = ex.result - hypothesis(ex.inputData);
            errorsum += (error * error);
        }

        return errorsum / size;

    }

    // collects training data
    public void report(int step, double accuracy) {
        trainingReport.add(step, accuracy);
    }

    public double alphaDecay(int time) {
        return 1000.0 / (1000.0 + time);
    }

    /*
     * pick data points at random apply single point update used with decreasing
     * learning rate (alpha decay) to achieve convergence
     */
    public void stochasticGradientDescent(int N, List<Example> examples) {
        initTrainingReport();
        // start with any (random) point in the parameter space
        initWeightVector();
        // loop for N steps
        Random random = new Random();

        for (int i = 0; i < N; i++) {
            // select a random training example
            Example ex = examples.get(random.nextInt(examples.size()));
            update(weightVector, ex, alphaDecay(i + 1));
            report(i + 1, accuracy(examples));
        }
    }

    /*
     * ADD COMMENTS!!!!!!
     */
    public void batchGradientDescent(int maxIteration, List<Example> examples) {
        initTrainingReport();
        initWeightVector();

        double alpha = 0.1;
        double convergenceDiff = 0.00001;
        double error = 0;
        double prevError = 0;
        double minAccuracy = 0.9;
        double accuracy = 0;

        int i = 0;

        do {
            i++;
            prevError = error;
            error = L2error(examples);
            for (Example ex : examples) {
                update(weightVector, ex, alpha);
            }

            accuracy = accuracy(examples);
            report(i, accuracy);

        } while ((Math.abs(prevError - error) > convergenceDiff || accuracy < minAccuracy) && i < maxIteration);

    }

}

	

	



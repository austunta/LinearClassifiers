import java.util.List;

public abstract class PerceptronClassifier extends LinearClassifier {


    public PerceptronClassifier(int inputNo) {
        super(inputNo);
    }

    @Override
    public double threshold(double point) { return (point>=0.0) ? 1.0 : 0.0;}

    //accuracy evaluation (correct results over all results)
    @Override
    public double accuracy(List<Example> examples) {
        double size = examples.size();
        double correct = 0.0;
        for (Example ex : examples) {
            if (hypothesis(ex.inputData) == ex.result)
                correct++;
        }
        return correct / size;
    }

    /*
     * perceptron learning rule update rule: weight <- weight + alpha (y-hw)xi
     * (equation: AIMA Eq. 18.7)
     */
    @Override
    public void update(double weightVector[], Example ex, double alpha) {
        int size = weightVector.length;
        double hw = hypothesis(ex.inputData);
        double difference = ex.result - hw;
        for (int i = 0; i < size; i++) {
            weightVector[i] += alpha * difference * ex.inputData[i];
        }

    }


}

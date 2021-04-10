import java.util.List;

public class LogisticClassifier extends LinearClassifier {


    public LogisticClassifier(int inputNo) {
        super(inputNo);
    }

    @Override
    public double threshold(double point) {
        return 1 / (1 + Math.exp(-point));
    }

    /*
     * normal accuracy evaluation (correct results over all results) cannot be used
     * with logistic classifier instead L2error evaluation is used to test accuracy
     */
    @Override
    public double accuracy(List<Example> examples) {
        return 1.0 - L2error(examples);
    }

    /*
     * logistic classifier update rule: weight <- weight + alpha(y-hw)hw*(1-hw)*xi
     * (equation: AIMA Eq. 18.8)
     */
    @Override
    public void update(double[] weightVector, Example ex, double alpha) {
        int size = weightVector.length;
        double hw = hypothesis(ex.inputData);
        double difference = ex.result - hw;
        for (int i = 0; i < size; i++) {
            weightVector[i] += alpha * difference * hw * (1 - hw) * ex.inputData[i];
        }

    }

}

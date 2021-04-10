import java.util.List;

public class LogisticBatchClassifier extends LogisticClassifier {


    public LogisticBatchClassifier(int inputNo) {
        super(inputNo);
    }

    @Override
    public void train(int steps, List<Example> examples) {
        batchGradientDescent(steps, examples);
    }
}

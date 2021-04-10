import java.util.List;

/*
 * substitutes batch gradient descent training method into generic train method
 */
public class PerceptronBatchGradientClassifier extends PerceptronClassifier {

    public PerceptronBatchGradientClassifier(int inputNo) {
        super(inputNo);
    }

    @Override
    public void train(int steps, List<Example> examples) {
        batchGradientDescent(steps, examples);
    }
}

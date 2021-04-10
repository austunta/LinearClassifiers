import java.util.List;

/*
 * substitutes stochastic training method into generic train method
 */
public class PerceptronStochasticClassifier extends PerceptronClassifier {

    public PerceptronStochasticClassifier(int inputNo) {
        super(inputNo);
    }

    @Override
    public void train(int steps, List<Example> examples) {
        stochasticGradientDescent(steps, examples);
    }
}

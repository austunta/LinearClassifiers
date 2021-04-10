import javax.sound.sampled.Line;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * arg0: name of the input file (that contains data related to examples)
 * arg1: steps (if stochastic chosen) or steps interpreted as maxIteration (if batch chosen)
 * arg2: fold number for crossValidation (enter 1 to use the learner without crossValidation)
 * arg3: boolean value for outputting training data (true or false)
 *
 * following values for batch gradient descent training can be changed within LinearClassifier class
 * convergence difference (precision)
 * alpha value
 *
 * this program trains four different types of linear classifiers on a given example set
 * and compares their average error values both on training data and validation data
 * also outputs a training data set as a csv file for each fold with "k fold cross validation" method
 * these data sets can be plotted easily with data visualization tools like Microsoft Excel
 */

public class Demo {

    public static void main(String args[]) throws IOException {
        //process arguments
        String fileName = args[0];
        String fileNameWithoutExtension = fileName.substring(0, fileName.indexOf("."));
        List<Example> examples = readData("dataFiles/" + fileName);
        int steps = Integer.parseInt(args[1]);
        int noInputs = examples.get(0).inputData.length;
        int foldNumber = Integer.parseInt(args[2]);
        boolean outputData = Boolean.parseBoolean(args[3]);
        String outputFileName = fileNameWithoutExtension + steps + "k" + foldNumber;

        //construct arrays to store average errors
        double[] errorSetPerceptronStochastic = null;
        double[] errorSetPerceptronBatch = null;
        double[] errorSetLogisticStochastic = null;
        double[] errorSetLogisticBatch = null;

        //construct linear classifiers
        LinearClassifier perceptronStochasticLearner = new PerceptronStochasticClassifier(noInputs);
        LinearClassifier perceptronBatchLearner = new PerceptronBatchGradientClassifier(noInputs);
        LinearClassifier logisticStochasticLearner = new LogisticStochasticClassifier(noInputs);
        LinearClassifier logisticBatchLearner = new LogisticBatchClassifier(noInputs);

        //cross validate all linear classifiers and store their average error values
        CrossValidation kCross = new CrossValidation();

        errorSetPerceptronStochastic = kCross.kCrossValidation(perceptronStochasticLearner, steps, foldNumber,
                examples, outputFileName + "PerceptronStochasticLearner", outputData);
        errorSetPerceptronBatch = kCross.kCrossValidation(perceptronBatchLearner, steps, foldNumber,
                examples, outputFileName + "PerceptronBatchLearner", outputData);
        errorSetLogisticStochastic = kCross.kCrossValidation(logisticStochasticLearner, steps, foldNumber,
                examples, outputFileName + "LogisticStochasticLearner", outputData);
        errorSetLogisticBatch = kCross.kCrossValidation(logisticBatchLearner, steps, foldNumber,
                examples, outputFileName + "LogisticBatchLearner", outputData);


        //print average error values for all linear classifiers
        System.out.printf("%nPERCEPTRON STOCHASTIC LEARNER on %s%nAVG TRAINING ERROR: %3f%nAVG VALIDATION ERROR: %3f%n",
                fileNameWithoutExtension, errorSetPerceptronStochastic[0], errorSetPerceptronStochastic[1]);
        System.out.printf("%nPERCEPTRON BATCH LEARNER on %s%nAVG TRAINING ERROR: %3f%nAVG VALIDATION ERROR: %3f%n",
                fileNameWithoutExtension, errorSetPerceptronBatch[0], errorSetPerceptronBatch[1]);
        System.out.printf("%nLOGISTIC STOCHASTIC LEARNER on %s%nAVG TRAINING ERROR: %3f%nAVG VALIDATION ERROR: %3f%n",
                fileNameWithoutExtension, errorSetLogisticStochastic[0], errorSetLogisticStochastic[1]);
        System.out.printf("%nLOGISTIC BATCH LEARNER on %s%nAVG TRAINING ERROR: %3f%nAVG VALIDATION ERROR: %3f%n",
                fileNameWithoutExtension, errorSetLogisticBatch[0], errorSetLogisticBatch[1]);

    }


    public static void printExamples(List<Example> examples) {
        for (Example e : examples) {
            System.out.println(e.toString());
        }
    }

    public static List<Example> readData(String input) throws IOException {

        List<Example> examples = new ArrayList<Example>();

        File inputFile = new File(input);
        BufferedReader bRead = new BufferedReader(new FileReader(inputFile));
        String inputLine;

        String[] lineData = null;
        int size = 0;
        int inputNo = 0;

        while ((inputLine = bRead.readLine()) != null) {
            lineData = inputLine.split(",");
            size = lineData.length;
            inputNo = size - 1;
            Example ex = new Example(size);
            ex.result = Double.parseDouble(lineData[inputNo]);
            for (int i = 1; i < size; i++) {
                ex.inputData[i] = Double.parseDouble(lineData[i - 1]);
            }
            ex.inputData[0] = 1;
            examples.add(ex);

        }
        bRead.close();
        return examples;
    }


}

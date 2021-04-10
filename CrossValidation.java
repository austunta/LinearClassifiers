import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CrossValidation {

    /*
     * starting from the beginning of the list (assume size of the list is x, fold size is k)
     * x/k th of examples becomes the validation set
     * rest becomes the training set
     * illustration, validation chunk = [.], training chunks = []
     * first fold: (validation)(k-x/k)
     * [.][][][]...[]
     * second fold: (x/k)(validation)(k-2x/k)
     * [][.][][]...[]
     * third fold: (2x/k)validation(k-3x/k)
     * [][][.][]...[]
     * ...
     * last fold: (k-x/k)+(validation)
     * [][][][]...[.]
     *
     * (pseudo code: AIMA Figure 18.8)
     */
    public double[] kCrossValidation(LinearClassifier learner, int steps, int k, List<Example> examples, String outputFileName, Boolean outputData) {
        double foldErrorT = 0;
        double foldErrorV = 0;
        double trainingErr = 0;
        double validationErr = 0;
        double foldError[] = new double[2];
        int setSize = examples.size();
        int chunkSize = setSize / k;

        List<Example> trainingSet = new LinkedList<Example>();
        List<Example> validationSet = new LinkedList<Example>();

        for (int fold = 0; fold < k; fold++) {

            partition(examples, trainingSet, validationSet, fold, chunkSize, setSize);

            learner.train(steps, trainingSet);

            //output the training data for each fold if wanted
            if (outputData) {
                try {
                    outputTrainingData(learner.trainingReport, outputFileName + "fold" + fold);
                    System.out.println("Training report for fold " + fold + " is created successfully");
                } catch (IOException e) {
                    System.out.println("ERROR occurred! Couldn't create a training report for fold " + fold);
                    e.printStackTrace();
                }
            }

            trainingErr = learner.L2error(trainingSet);
            foldErrorT += trainingErr;
            validationErr = learner.L2error(validationSet);
            foldErrorV += validationErr;
			System.out.println("CROSS NUMBER " + fold);
			System.out.printf("training error: %3f%n",trainingErr);
			System.out.printf("validation error: %3f%n",validationErr);
        }

        foldError[0] = foldErrorT / k;
        foldError[1] = foldErrorV / k;
        return foldError;
    }


    /*
     *
     * []= chunks, validation chunk = [.]
     * [][]...[.][][]...[]
     * first [.] is added to the validation set
     * then all the chunks up to the validation chunk are added to the training set
     * then all the chunks from the validation chunk up to the end are added to the training set
     */
    private void partition(List<Example> examples, List<Example> trainingSet, List<Example> validationSet, int fold,
                           int chunkSize, int setSize) {

        validationSet.clear();
        trainingSet.clear();

        int validStartIndex = fold * chunkSize;
        int validEndIndex = validStartIndex + chunkSize;

        for (int i = validStartIndex; i < validEndIndex; i++)
            validationSet.add(examples.get(i));

        for (int i = 0; i < validStartIndex; i++)
            trainingSet.add(examples.get(i));

        for (int i = validEndIndex; i < setSize; i++)
            trainingSet.add(examples.get(i));

    }

    public void outputTrainingData(List<Double> data, String output) throws IOException {
        File outputFile = new File(output + ".csv");
        BufferedWriter buffWriter = new BufferedWriter(new FileWriter(outputFile));
        outputFile.createNewFile();
        int size = data.size();
        for (int i = 0; i < size - 1; i++) {
            buffWriter.write(i + 1 + "," + data.get(i + 1) + ",");
            buffWriter.newLine();
        }
        System.out.println("TRAINING DATA OUTPUTTED SUCCESSFULLY");
        buffWriter.flush();
        buffWriter.close();
    }

}

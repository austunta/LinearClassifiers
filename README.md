# LinearClassifiers
Java implementation of 4 types of linear classifiers (combinations of logistic/perceptron classifiers with batch/stochastic gradient descent)

On a given dataset, this program trains:
- Logistic classifier with batch gradient descent
- Logistic classifier with stochastic gradient descent 
- Perceptron classifier with batch gradient descent
- Perceptron classifier with stochastic gradient descent

By using K-Fold Cross Validation technique, for a specific dataset, these linear classifiers are compared on their average error values on both the training data and the validation data. These error values are printed on the terminal so that the user can decide which classifier performs better on a given dataset. 

While running K-Fold Validation, for each model (k models for every type of linear classifier), the training dataset (training set accuracy at the nth weight update) is outputted as a csv file. This training data can be plotted easily with a data visualization tool like Microsoft Excel if the user wishes to see the learning curve. The user can opt out from generating this dataset by entering false as the 4th argument.

How to run? 

This program accepts 4 arguments: 
arg0: name of the input file (that contains the dataset) (e.g. IrisData.txt)
arg1: number of steps (for stochastic gradient descent)/maximum number of iterations (for batch gradient descent) (e.g. 5000)
arg2: fold number (k) for Cross Validation  (e.g. 10)
arg3: boolean value for outputting training data (e.g. true) 

Technical choices made for batch gradient descent implementation

- Decreasing learning rate (decaying alpha) is use to increase the chances of convergence
Learning rate schedule is set to 1000/(1000+t)   

Technical choices made for batch gradient descent implementation

- Learning rate (alpha) is set to 0.1 
- Convergence Difference (precision) is set to 0.00001

Some problems and implemented solutions for batch gradient descent training

Late convergence: Although batch gradient descent will eventually converge, sometimes it may take longer than what user can tolerate. So argument 1 (number of steps) is interpreted as maximum number of iterations to set a limit for the cases of late convergence. 

Premature Convergence: For some datasets, early weight updates do not significantly move the learner to a point in the weight space with less loss (the effect the training set accuracy do not change much). This situation might cause the learner prematurely terminate as it reaches the convergence difference falsely because of the little difference in the training set accuracy. In order to prevent this situation minimum accuracy is set to 90% so that the learner does not prematurely reach convergence before it learns the dataset "enough". How much accuracy is enough can be changed by the user by changing the value of minAccuracy variable in the batch gradient descent implementation. 


Datasets for Training 

Two example datasets are provided for the user:

IrisData.txt -  Each example is a set of 4 numbers representing sepal length, sepal width, petal length, petal width in cm. The original dataset contains three output values, the formatted dataset only includes the data for two output values: Iris-setosa and Iris-versicolor. These output values are replaced with 1 and o respectively. 

SonarData.txt: Each example is a set of 60 numbers representing the energy within a particular frequency band (UCI Machine Learning Repository). The original output values R (rock) and M (mine) are replaced with 1 and 0 respectively.  

If the user wants to experiment with other datasets, the dataset has to comply with formatting rules mentioned below. 

Dataset Formatting

This program uses a custom data reading method. Unless the user wishes to write their own data reading method, the dataset for which the linear classifiers will be tested on has to be in the following format:
- A text file 
- Each example has to be on a separate line 
- For each example, data parameter values (input attribute values) has to be separated by columns, and the last value has to be the output 

Please see IrisData.txt and SonarData.txt in dataFiles folder. 


Citations 

Linear regression algorithms are taken from Aritifical Intelligence Modern Approach (AIMA)

AIMA 3rd ed - Russell, S. J., &amp; Norvig, P. (2010). Artificial intelligence: A modern approach. Upper Saddle River, NJ: Prentice Hall.

Datasets are taken from "UCI Machine Learning Depository."

Sonar Data - R. Paul Gorman and Terry Sejnowski. UCI Machine Learning Repository [http://archive.ics.uci.edu/ml/datasets/connectionist+bench+(sonar,+mines+vs.+rocks]. Irvine, CA: University of California, School of Information and Computer Science. 

Iris Data - R.A. Fisher and Michael Marshall. UCI Machine Learning Repository [http://archive.ics.uci.edu/ml/datasets/iris. Irvine, CA: University of California, School of Information and Computer Science. 

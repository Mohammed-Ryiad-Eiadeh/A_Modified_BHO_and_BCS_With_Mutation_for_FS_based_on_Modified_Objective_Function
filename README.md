# Investigating the Performance of a Novel Modified Binary Black Hole Optimization Algorithm for Enhancing Feature Selection

# Abstract
High dimensional datasets are highly likely to have redundant, irrelevant, and noisy features that negatively affect the performance of the classification algorithms. Selecting the most relevant features and reducing the dimensions of datasets by removing the undesired features is a dimensional reduction technique called Feature Selection (FS). In this paper, we propose an FS approach based on the Black Hole Algorithms (BHO) with a mutation technique called MBHO. Generally, BHO contains two major phases. At the exploitation phase, a set of stars are modified based on some rule and according to some objective function, the best star is selected as the black hole which attracts other stars. Furthermore, when a star gets closer to the event horizon, it will be swallowed and a new one will be randomly generated in the search space which thus is the exploration phase. However, randomness may cause the algorithm to fall into the trap of local optima, and to overcome such complications, inversion mutation is used. Furthermore, we modify a widely utilized objective function in most of the proposed works for wrapper feature selection by combining two new terms that are based on the correlation among the selected subset of features and the features and the classification label. We also utilize a transfer function, known as the V2 transfer function, to convert continuous values into discrete ones to enhance search. We assess our approach via extensive evaluation experiments using fourteen benchmark datasets. We benchmark the performance of a wrapper FS approach called Binary Cuckoo Search (BCS), and three filter-based FS approaches (namely Mutual Information Maximisation (MIM), Joint Mutual Information (JMI), and minimum Redundancy Maximum Relevance (mRMR)). Our evaluation has shown that the proposed model is an effective approach for FS, in selecting better features that enhance the performance metrics on the classifiers. Thus, MBHO can be utilized as one alternative to the existing state-of-art-approaches. We release the source codes of our implementation for the community to build on with new methods and datasets.

# Fitness Function
$f(X) = \max\left(Acc(X) + w_f \left(\left(1 - \frac{L_f}{L_t}\right) - \text{Correlation}(X) + \text{Correlation}(X, l)\right)\right)$

$Acc:$ is the classification accuracy provided by the given classifier over the subset of features $X$.

$w_f:$ is the weight factor relied in (0,1].

$L_f:$ is the number of features in the selected subset $X$.

$L_t:$ is the total number of features in the given dataset.

$Correlation(X):$ is the correlation among the features in $X$.

$Correlation(X,l)$ is the correlation between each feature and the label $l$. 

# Our Contribution
1) We propose a novel wrapper feature selection (FS) approach, that combines the black hole algorithm with inversion mutation,  to select the most descriptive subset of features from datasets that cover different application domains.

2) We modify a well-established multi-objective function that focuses on the interleaved classifier and the number of selected features in the decision-making process. Additionally, we enhance the decision-making process by considering the correlation among the selected subset of features and the correlation of each feature within that subset with the corresponding label.

3) We assess our approach using fourteen benchmark datasets. We benchmark the performance of a wrapper FS approach called Binary Cuckoo Search (BCS). We also benchmark the performance of three filter-based FS, namely Mutual Information Maximisation (MIM), Joint Mutual Information (JMI) and minimum Redundancy Maximum Relevance (mRMR).

# Datasets We Used In Our Work
These datasets are sourced from the UCI ML repository [1]. The datasets are colon, Darwin, divorce, WDBC, leukemia, leukemia-3c, MLL, Parkinsons, sobar, sonar, SPECTFtest, SRBCT, urban, and WPBC. Colon dataset is widely used in binary classification, especially, predicting Colon cancer. Darwin dataset is widely used in ML tasks such as classification and regression analysis. Leukemia and Leukemia-3c datasets demonstrate various molecular or cellular features, widely used in machine learning tasks and differing in size. The MLL dataset includes various molecular or cellular features, widely utilized in data science tasks. The WDBC dataset represents various diagnostic measurements crucial for breast cancer research and analysis. The SRBCT dataset embodies diverse molecular or cellular attributes pertinent to machine learning research and analysis in the context of small round blue cell tumors. The Sobar dataset encapsulates diverse socio-behavioral features, facilitating research and analysis in relevant domains. The Parkinson's dataset represents various clinical and demographic features relevant to Parkinson's disease research and diagnosis. The Sonar dataset represents distinct acoustic signal features, valuable for research and analysis in underwater target detection or classification tasks. The Divorce dataset represents diverse socio-demographic and relationship features, crucial for machine learning research and analysis in understanding factors valuable in divorce prediction or prevention. The SpectTF dataset embodies various attributes related to spectral analysis or signal processing, pertinent for research and analysis in fields such as pattern recognition or medical diagnostics. The Urban dataset represents diverse urban features, facilitating research and analysis in urban planning, development, or sustainability studies. The WPBC dataset represents various clinical and demographic features pertinent to research and analysis in the context of breast cancer prognosis and treatment. As detailed above, this diverse selection of datasets with different applications helps us in better generalization of the performance of our FS approach for different datasets with different characteristics. A detailed description of all these datasets can be found in Table 1. These datasets were specifically selected due to certain characteristics such as high feature size, and binary classes, which are ideal for demonstrating the effectiveness of the feature selection algorithms (both our proposed one and those used as baselines in our evaluation). We underscore that the benefits of our suggested model apply to any given dataset with many features that need a careful and accurate FS approach.

| Name         | No. of Attributes | No. of Instances | No. of Classes |
|--------------|-------------------|-------------------|-----------------|
| Colon        | 2000              | 62                | 2               |
| Darwin       | 450               | 174               | 2               |
| Leukemia     | 3571              | 72                | 2               |
| Leukemia-3c  | 7129              | 72                | 2               |
| MLL          | 12582             | 72                | 3               |
| WDBC         | 30                | 569               | 2               |
| SRBCT        | 2308              | 83                | 4               |
| Sobar        | 19                | 72                | 2               |
| Parkinsons   | 22                | 197               | 2               |
| Sonar        | 60                | 208               | 2               |
| Divorce      | 54                | 170               | 2               |
| SpectTF      | 44                | 267               | 2               |
| Urban        | 146               | 675               | 4               |
| WPBC         | 31                | 198               | 2               |

# Parameter Configuration of Our Experiments
We begin by detailing the primary hyperparameters utilized in various components 680 of our framework. We use the KNN algorithm in the evaluation process with k = 3 and 681 the Manhattan distance function to measure the dissimilarity among a pair of instances. 682 Also, we use k-fold cross-validation with 10-fold for better evaluation. The parameters for 683 the MBHO were selected as follows: maximum iterations (M = 25), population size which 684 refers to a set of potential attack paths (N = 10), mutation rate (mr = 0.5), and weight 685 factor (w f = 0.001). For the binary CS [2], we have set population size N = 30, step-size 686 = 1.5, lambda = 2.5, worst-nest-probability = 0.2, delta = 1.5, mr = 0.5, and M = 25. 687 All tests were conducted using the Java language (JDK 17) on a machine with an Intel® 688 Core™ i7-8750H CPU @ 2.20GHz (12 CPUs), and 32768MB RAM. The tools and libraries we 689 used in this study are as follows: Java Development Kit 8 (JDK 8), Integrated Development 690 Environment (IDE), IntelliJ ultimate version, Oracle Machine Learning Library, Tribuo 4.1.0, 691 and XChart Library 3.8.0. Tribuo is a general-purpose open-source ML library written in 692 Java that can be used for deep learning and Natural Language Processing applications 693 as well [3]. Furthermore, we use the Apache Common Math 3 API for correlation 694 calculation.

# Comparison of MBHO and  on all datasets
This is a comparsion among MBHO with the Spearman correlation function and the methods MIM [4], JMI [5], and mRMR [6]. Here, we matched the number of features selected by our approach with the number of features used by all three filters. Our model consistently achieved superior results in terms of accuracy ($Acc$) and F1 score across all datasets. However, in most cases, it required more time to generate the final subset of features.

| Name         | Features | MBHO-correlation (Acc) | MBHO-correlation (F1) | MIM (Acc) | MIM (F1) | JMI (Acc) | JMI (F1) | mRMR (Acc) | mRMR (F1) |
|--------------|----------|-------------------------|------------------------|------------|----------|-----------|----------|------------|----------|
| Colon        | 1064     | **0.86**                | 0.83                   | 0.79       | 0.76     | 0.79      | 0.76     | 0.76       | 0.73     |
| Darwin       | 258      | **0.87**                | 0.86                   | 0.76       | 0.74     | 0.75      | 0.72     | 0.75       | 0.72     |
| Leukemia     | 1732     | **0.99**                | 0.94                   | **0.99**   | 0.94     | **0.99**  | 0.94     | **0.99**   | 0.94     |
| Leukemia-3c  | 3508     | **0.97**                | 0.87                   | 0.93       | 0.85     | 0.93      | 0.85     | 0.93       | 0.85     |
| MLL          | 6500     | **0.94**                | 0.87                   | **0.94**   | 0.87     | 0.93      | 0.85     | 0.93       | 0.85     |
| WDBC         | 14       | **0.95**                | 0.94                   | 0.93       | 0.93     | 0.93      | 0.93     | 0.94       | 0.93     |
| SRBCT        | 1201     | **0.95**                | 0.90                   | **0.95**   | 0.89     | 0.90      | 0.85     | **0.95**   | 0.89     |
| Sobar        | 6        | **0.94**                | 0.85                   | 0.89       | 0.77     | 0.86      | 0.74     | 0.92       | 0.79     |
| Parkinsons   | 10       | **0.91**                | 0.87                   | 0.86       | 0.79     | 0.87      | 0.88     | 0.85       | 0.77     |
| Sonar        | 29       | **0.92**                | 0.91                   | 0.87       | 0.86     | 0.83      | 0.83     | 0.86       | 0.86     |
| Divorce      | 14       | **0.99**                | 0.99                   | 0.98       | 0.97     | 0.98      | 0.97     | 0.98       | 0.97     |
| SpectTF      | 26       | **0.84**                | 0.84                   | 0.76       | 0.63     | 0.76      | 0.65     | 0.72       | 0.58     |
| Urban        | 80       | **0.77**                | 0.72                   | 0.64       | 0.60     | 0.70      | 0.66     | 0.69       | 0.62     |
| WPBC         | 13       | **0.78**                | 0.65                   | 0.70       | 0.53     | 0.68      | 0.56     | 0.70       | 0.54     |

$F1:$ is the fitness score.



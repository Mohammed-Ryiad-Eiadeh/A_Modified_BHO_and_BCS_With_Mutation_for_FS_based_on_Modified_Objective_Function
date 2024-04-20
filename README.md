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
These datasets are sourced from the UCI ML repository \cite{Dua:2019}. The datasets are colon, Darwin, divorce, WDBC, leukemia, leukemia-3c, MLL, Parkinsons, sobar, sonar, SPECTFtest, SRBCT, urban, and WPBC. Colon dataset is widely used in binary classification, especially, predicting Colon cancer. Darwin dataset is widely used in ML tasks such as classification and regression analysis. Leukemia and Leukemia-3c datasets demonstrate various molecular or cellular features, widely used in machine learning tasks and differing in size. The MLL dataset includes various molecular or cellular features, widely utilized in data science tasks. The WDBC dataset represents various diagnostic measurements crucial for breast cancer research and analysis. The SRBCT dataset embodies diverse molecular or cellular attributes pertinent to machine learning research and analysis in the context of small round blue cell tumors. The Sobar dataset encapsulates diverse socio-behavioral features, facilitating research and analysis in relevant domains. The Parkinson's dataset represents various clinical and demographic features relevant to Parkinson's disease research and diagnosis. The Sonar dataset represents distinct acoustic signal features, valuable for research and analysis in underwater target detection or classification tasks. The Divorce dataset represents diverse socio-demographic and relationship features, crucial for machine learning research and analysis in understanding factors valuable in divorce prediction or prevention. The SpectTF dataset embodies various attributes related to spectral analysis or signal processing, pertinent for research and analysis in fields such as pattern recognition or medical diagnostics. The Urban dataset represents diverse urban features, facilitating research and analysis in urban planning, development, or sustainability studies. The WPBC dataset represents various clinical and demographic features pertinent to research and analysis in the context of breast cancer prognosis and treatment. As detailed above, this diverse selection of datasets with different applications helps us in better generalization of the performance of our FS approach for different datasets with different characteristics. A detailed description of all these datasets can be found in Table 1. These datasets were specifically selected due to certain characteristics such as high feature size, and binary classes, which are ideal for demonstrating the effectiveness of the feature selection algorithms (both our proposed one and those used as baselines in our evaluation). We underscore that the benefits of our suggested model apply to any given dataset with many features that need a careful and accurate FS approach.

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


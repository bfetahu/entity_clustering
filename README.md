# Entity Clustering Application
The 'entity_clustering' application, is a Java application that perform entity clustering from RDF datasets, like the BTC dataset. The clustering is performed through two main clustering techniques: 1) X-Means[<a href='http://dl.acm.org/citation.cfm?id=657808'>1</a>] and 2) Spectral Clustering[<a href='http://link.springer.com/article/10.1007%2Fs11222-007-9033-z'>2</a>]. 
Considering the scale of the dataset we first bucket the entities through the LSH (Locality-Sensitive Hashing algorithm) such that we have less pairwise comparisons during the clustering process.

The main classess in this Java application are: EntityClustering and LSHClustering. 

###Compiling
The application runs under the **Java 8** version, hence, please make sure you have the desired version installed. Please compile the application through maven. 
```mvn clean compile```

##Entity Clustering

The 'EntityClustering' the main class which holds all the operations for generating the entity features that are used for the clustering and the clustering techniques itself. Below we list all the possible operations for this class and the required parameters.

###Parameters
* P1. ```operation```
* P2. ```btc_data```
* P3. ```out_dir```
* P4. ```data_file```
* P5. ```lsh_bins```
* P6. ```stop_words_path```
* P7. ```type_filter```
* P8. ```type_index_path```
* P9. ```loaded_type_index```
* P10. ```k```
* P11. ```ngram```

###Running
```java -cp entity_clustering.jar:libs/* EntityClustering -operation VAL1 -btc_data VAL2 -out_dir VAL3 -data_file VAL4 -lsh_bins VAL5 -stop_words_path VAL6 -type_filter VAL7 -type_index_path VAL8 -loaded_type_index VAL9 -k VAL10 -ngram VAL11```


###Operations

####P1:  type_index

Operation: generate the type index where every entity is associated with at least on of the types it belongs. We assign an entity to only one entity type to avoid performing the clustering process on entities more than once (due to the fact that entities might belong to more than one entity type).

Required parameters: **P2, P3, P7**

Values: 
* P2 - path to the BTC directory (files need to be compressed with the standard gzip format).
* P3 - path to the output directory where the type index will be stored.
* P7 - path to the file containing the types which we do not want to include in our clustering pipeline.

####P1:  entity_type_index

Operation: Restructure the RDF datasets into type-specific files that contain all entities assigned to the specific types. 

Required parameters: **P2, P3, P8**

Values:
* P2 - path to the BTC directory (files need to be compressed with the standard gzip format).
* P3 - path to the output directory where we store the entity profiles on separate files for the different entity types.
* P8 - path to the entity type index created through the operation _'type_index'_.

####P1:  entity_cluster_profiles

Operation: This is a pre-processing step for generating the entity feature vectors. Here we fetch all textual and structural literals. The output format here is a line per entity, clearly distinguishing between the textual and structural values.

Required parameters: **P3, P4, P6, P8, P9**

Values:
* P3 - path to the output directory where we store the entity profiles on separate files for the different entity types.
* P4 - path to the type specific entity profiles (consisting of all the triples) generated based on the type index
* P6 - path to the file containing the stop words (delimmitted by \n)
* P8 - path to the entity type index created through the operation _'type_index'_.
* P9 - path to the entity type index, where for an entity we have all the possible types it belongs to. In this way we will ensure that an entity is included only once for the clustering process in one of its types.

####P1:  type_instances_features

Operation: Generates the type specific entity features, consisting of the textual and structural features. In addition we create the dictionaries for the feature items (structural and textual), specific to the entity types. This is used later on to construct the feature vector space for entities and hence have unique identifiers for the feature items. An additional advantage of having unique IDs for the feature items is that we can construct sparse feature vectors (i.e., for an entity the feature vector consists of only the feature items that occurr in its profile).

Required parameters: **P3, P4, P6, P11**

Values:
* P3 - path to the output directory where we store the textual and structural entity features specific to a particular entity type.
* P4 - path to the type specific entity profiles (consisting of all the triples) generated based on the type index
* P6 - path to the file containing the stop words (delimmitted by \n)
* P12 - the maximum n-gram size

####P1:  cluster_features

Operation: Here we generate the sparse feature vectors. We additionally remove feature items that have low occurrence frequency (since they do not gain anything during the clustering process as they contain highly distinctive information between entities).

Required parameters: **P3, P4, P5, P6**

Values:
* P3 - path to the output directory where we store the final sparse entity feature vectors.
* P4 - path to the type specific entity textual and structural features.
* P5 - path to the computed LSH entity buckets (see _'LSH Clustering'_ section)
* P6 - path to the file containing the stop words (delimmitted by \n)

####P1:  xmeans

Operation: Performs the X-means clustering on the pre-computed sparse entity features.

Required parameters: **P3, P4, P5, P6**

Values:
* P3 - path to the output directory where we store the final sparse entity feature vectors.
* P4 - path to the sparse entity feature vectors
* P5 - path to the computed LSH entity buckets
* P6 - path to the file containing the stop words (delimmitted by \n)
* P10 - maximum number of clusters

####P1:  spectral

Operation: Performs the spectral clustering on the pre-computed sparse entity features.

Required parameters: **P3, P4, P5, P6**

Values:
* P3 - path to the output directory where we store the final sparse entity feature vectors.
* P4 - path to the sparse entity feature vectors
* P5 - path to the computed LSH entity buckets
* P6 - path to the file containing the stop words (delimmitted by \n)
* P10 - maximum number of clusters

##LSH Clustering

Operation: Performs the entity bucketing based on the LSH clustering algorithm.

Required parameters and Values: 
* **P1** ```out_dir```: the path to the output directory containg all the computed entity buckets
* **P2** ```data_file```: the path to the directory containing the computed entity type specific features (an output generated by the `Entity Clustering' process with _operation_ **type_instances_features**)
* **P3** ```sample```: in case you want to compute the buckets only for a sample of entities. 

###Running:
```java -cp entity_clustering.jar:libs/* LSHClustering -out_dir VAL1 -data_file VAL2 -sample VAL3```
##Compiling

Please download the <a href='http://l3s.de/~fetahu/tools/utils-1.0.jar'>utils.jar</a> library and install into your own local maven repository. It contains I/O methods that are used throughout the project.

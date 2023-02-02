# TP 4 - OULAD Project

The aim of this project is to perform an analysis of the OULAD Dataset using Spark's
SQL/Dataframe API & MLlib.

The OULAD Dataset can be downloaded form the [official website](https://analyse.kmi.open.ac.uk/open_dataset).
The website also contains a detailed description of the dataset.

## Getting Started

Once downloaded, unzip the dataset and upload all resulting csv files to HDFS.
Next, start a Jupyter notebook as in [TP3 Getting Started](../3/README.md#getting-started).


For the exercises, each group will have to choose it's `code_module` between (`BBB` and `GGG`).


## Computing some statistics

- Load the `studentInfo` table into a Spark Dataframe.
- Select all students that have your selected `code_module`.

Questions:

- How many students have your `code_module` in total?
- How many students have passed (`final_result == PASS`) in total?
- How many students have passed (`final_result == PASS`) in the 2014B group (`code_presentation == 2014B`)?
- How many students have passed (`final_result == PASS`) in the 2014J group (`code_presentation == 2014J`)?
- What is the average `studied_credits` of male students (`gender == M`) and female students (`gender == F`) in the 2014J group
(`code_presentation == 2014J`) that have a HE Qualification or an A level or equivalent (`highest_education == HE Qualification or highest_education == A level or equivalent`)
- Replace the `age_band` column with the minimum value ("0-35" => 0, "35-55" => 35, "55<=" => 55) and compute it's average.
- Compute the repartition of `final_result` by `gender`, then by `highest_education`.


## Statistics with Joins

- Compute the total `sum_click` by student that have your selected `code_module`. (`studentInfo` & `studentVle`)
- List distinct `activity_type`s available in the course (`studentInfo` & `vle`)
- Compute the total `sum_click` by distinct `activity_type`s performed by students that have 
  your selected `code_module` (`studentInfo` & `studentVle` & `vle`).


## Prediction

Using the `studentInfo` tables features we want to predict the `final_result`.
To achieve that, run the RandomForest classifier on the `studentInfo` table (filtered by your selected `code_module`).
Do not forget to convert categorical variables into numerical values (label indexation or one-hot-encoding) and split the data into a training and testing set.

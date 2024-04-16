# TP 4.1 - OULAD Project

The aim of this project is to perform an analysis of the OULAD Dataset using Spark's
SQL/Dataframe API & MLlib.

The OULAD Dataset can be downloaded form the [official website](https://analyse.kmi.open.ac.uk/open_dataset).
The website also contains a detailed description of the dataset.

## 4.1.1 Getting Started

Once downloaded, unzip the dataset and upload all resulting csv files to HDFS.
Next, start a Jupyter notebook as in [TP3 Getting Started](../3/README.md#getting-started).


For the exercises, each group will have to choose it's `code_module` between (`BBB` and `GGG`).


## 4.1.2 Computing some statistics

1. Load the `studentInfo` table into a Spark Dataframe.
2. Select all students that have your selected `code_module`.

Questions:

3. How many students have your `code_module` in total?
4. How many students have passed (`final_result == Pass`) in total?
5. How many students have passed (`final_result == Pass`) in the 2014B group (`code_presentation == 2014B`)?
6. How many students have passed (`final_result == Pass`) in the 2014J group (`code_presentation == 2014J`)?
7. What is the average `studied_credits` of male students (`gender == M`) and female students (`gender == F`) in the 2014J group
(`code_presentation == 2014J`) that have a HE Qualification or an A level or equivalent (`highest_education == HE Qualification or highest_education == A Level or Equivalent`)
8. Replace the `age_band` column with the minimum value ("0-35" => 0, "35-55" => 35, "55<=" => 55) and compute it's average.
9. Compute the repartition of `final_result` by `gender`, then by `highest_education`.


## 4.1.3 Statistics with Joins

1. Compute the total `sum_click` by `region` that have your selected `code_module` (`studentVle` & `studentInfo`).
2. List distinct `activity_type`s accessed by students in the course (`studentVle` & `vle`).
3. Compute the total `sum_click` by distinct `activity_type`s performed by students that have 
   your selected `code_module` (`studentInfo` & `studentVle` & `vle`).
4. Compute the mean TMA (`assessment_type`) assessment `score` by `gender` for students
   that registered (`date_registration`) after day -3 and unregistered (`date_unregistration`)
   before day 160. (`studentInfo` & `studentRegistration` & `studentAssessment` & `assessments`)


## 4.1.4 Prediction

1. Using the `studentInfo` tables features we want to predict the `final_result`.
   To achieve that, run the RandomForest classifier on the `studentInfo` table (filtered by your selected `code_module`).
   Do not forget to convert categorical variables into numerical values (label indexation or one-hot-encoding) and split the data into a training and testing set.
   Assess the model prediction quality using the `MulticlassClassificationEvaluator` `accuracy` score.
2. To improve prediction accuracy, we want to enhance the `studentInfo` table with student interaction
   data from `student_vle`, `vle`, `assessments` and `student_assessments`.
   To achieve that, compute the following "Learning indicators" and add them to the `studentInfo` table:
   - Total sum of clicks (`student_vle`) for each `vle.activity_type` (add one column for each activity type).
   - Number of assessments handed on time `assessment.date > student_assessment.date_submitted`.
   - Sum of weighted assessment scores (`student_assessment.score` * `assessment.weight` / 100).
3. Re-run the prediction model. How well does it perform?


# TP 4.2 - Ad click prediction

The aim of this project is to perform an analysis of the
[Ad click dataset](https://www.kaggle.com/datasets/ruchikajain/add-data).

## 4.2.1 Getting Started

Once downloaded, unzip the dataset and upload the csv file to HDFS.
Next, start a Jupyter notebook as in
[TP3 Getting Started](https://github.com/SergioSim/debian-hadoop/blob/master/tp/3/README.md#getting-started).

## 4.2.2 Computing some statistics

1. Load the dataset into a Spark Dataframe.
2. Retrieve the number of rows.
3. Drop the `Year` and `VistID` columns.
4. Compute the distribution of the `Clicked` column. Is there an imbalance?
5. Which `Month` has the most rows?
    1. Which `Month` has the most `Clicks`?
    2. During the `Month` with the most `Clicks`, compute the distribution of `Clicks` by
      `Time_Period`.
6. How many distinct `Ad_Topics` are there?
    1. Which `Ad_Topic` is the most effective (highest mean `Click` rate)?
    2. Which `Ad_Topic` is the least effective?
    3. Which `Ad_Topic` has received the most `Clicks`?
    4. How many `Clicks` did the most effective `Ad_Topic` generate?
7. Compare the `Click` rate of 5 countries (`Country_Name`) with the highest `Avg_Income`
   With the `Click` rate of the 5 countries (`Country_Name`) with the lowest `Avg_Income`
8. How much are the `Avg_Income` and `Internet_Usage` columns correlated?
9. Are `Time_Spent`,`Avg_Income`, `Internet_Usage` or `Male` columns correlated with `Click`?


## 4.2.3 Prediction

We want to predict the `Click` column.
Try to run a `LogisticRegression` Spark ML classifier.
Do not forget to convert categorical variables into numerical values
(label indexation or one-hot-encoding) and split the data into a training and testing set.


# TP 4.3 - Sales clickstream

The aim of this project is to perform an analysis of the
[E-Shop Clothing Online Dataset](https://www.kaggle.com/datasets/heninursafitri/clothi).

The dataset consists of a table containing sales records (one row for each sold item).

## 4.3.1 Getting Started

Once downloaded, unzip the dataset and upload the csv file to HDFS.
Next, start a Jupyter notebook as in
[TP3 Getting Started](https://github.com/SergioSim/debian-hadoop/blob/master/tp/3/README.md#getting-started).

## 4.3.2 Computing some statistics

1. Load the dataset into a Spark Dataframe.
2. Retrieve the number of rows.
3. Drop the `year` column.
4. Which `clothing model` has the highest `price`?
    1. How many times was it sold in total?
    2. How many times was it sold in the 4th `month`?
5. Which `session` resulted in the highest total `price`?
6. How many distinct `clothing models` in the `main category` 1 were sold during the 6th `month`?
7. Which `month` contains the highest number of sales?
    1. Find the 5 most popular `clothing models` during this month.
    2. What percentage of total sales in this month do the 5 most popular `clothing models` account for?
    3. To which `page` category do this `clothing models` belong to?
8. Do `prices` change over time for `clothing models`?
9. Compute the average `price` by `main category`.
    - Which `clothing models` in the 1st `main category` have a higher average price and are classed in the 2nd `price 2` category?

## 4.3.3 Streaming

1. start a Netcat server in the VM with:
  `nc -lk 9999`
2. Create a Spark Structured Streaming Dataframe that reads from the Netcat server
3. Manually copy/paste some of the first lines into the netcat server.
  Or alternatively, you can automate line insertion with something like:
  ```bash
  function slowcat(){ while read; do sleep .1; echo "$REPLY"; done; }
  cat e-shop\ clothing\ 2008.csv | slowcat | nc -lk 999
  ```
  Source: [stackoverflow](https://stackoverflow.com/a/46968824)
4. Recompute the previous statistics while inserting more lines into the netcat server.


## 4.4 Brute force

We want to recover an old password we have forgot.

We remember the following:

- The md5 sum is `003ee70bc7177b11065d15d3c6d31fdd` (calculated using `pyspark.sql.functions.md5`)
- It contains the string "Password"
- It's between 9-13 characters long.
- It consists of only upper and lower case letters [a-zA-Z] and `!`.

Simpler version:

- The md5 sum is `23453a62372b537c2acf48b8d8caeff1`
- It's 11 characters long.
- It consists of only upper and lower case letters [a-zA-Z] and `!`.

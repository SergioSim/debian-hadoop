# TP 4.1 - OULAD Project

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
- How many students have passed (`final_result == Pass`) in total?
- How many students have passed (`final_result == Pass`) in the 2014B group (`code_presentation == 2014B`)?
- How many students have passed (`final_result == Pass`) in the 2014J group (`code_presentation == 2014J`)?
- What is the average `studied_credits` of male students (`gender == M`) and female students (`gender == F`) in the 2014J group
(`code_presentation == 2014J`) that have a HE Qualification or an A level or equivalent (`highest_education == HE Qualification or highest_education == A level or Equivalent`)
- Replace the `age_band` column with the minimum value ("0-35" => 0, "35-55" => 35, "55<=" => 55) and compute it's average.
- Compute the repartition of `final_result` by `gender`, then by `highest_education`.


## Statistics with Joins

- Compute the total `sum_click` by `region` that have your selected `code_module` (`studentVle` & `studentInfo`).
- List distinct `activity_type`s available in the course (`studentInfo` & `vle`).
- Compute the total `sum_click` by distinct `activity_type`s performed by students that have 
  your selected `code_module` (`studentInfo` & `studentVle` & `vle`).
- Compute the mean TMA (`assessment_type`) assessment `score` by `gender` for students
  that registered (`date_registration`) after day -3 and unregistered (`date_unregistration`)
  before day 160. (`studentInfo` & `studentRegistration` & `studentAssessment` & `assessments`)


## Prediction

Using the `studentInfo` tables features we want to predict the `final_result`.
To achieve that, run the RandomForest classifier on the `studentInfo` table (filtered by your selected `code_module`).
Do not forget to convert categorical variables into numerical values (label indexation or one-hot-encoding) and split the data into a training and testing set.

# TP 4.2 - Ad click prediction

The aim of this project is to perform an analysis of the
[Ad click dataset](https://www.kaggle.com/datasets/ruchikajain/add-data).

## Getting Started

Once downloaded, unzip the dataset and upload the csv file to HDFS.
Next, start a Jupyter notebook as in
[TP3 Getting Started](https://github.com/SergioSim/debian-hadoop/blob/master/tp/3/README.md#getting-started).

## Computing some statistics

- Load the dataset into a Spark Dataframe.
- Retrieve the number of rows.
- Drop the `Year` and `VistID` columns.
- Compute the distribution of the `Clicked` column. Is there an imbalance?
- Which `Month` has the most rows?
    - Which `Month` has the most `Clicks`?
    - During the `Month` with the most `Clicks`, compute the distribution of `Clicks` by
      `Time_Period`.
- How many distinct `Ad_Topics` are there?
    - Which `Ad_Topic` is the most effective (highest mean `Click` rate)?
    - Which `Ad_Topic` is the least effective?
    - Which `Ad_Topic` has received the most `Clicks`?
    - How many `Clicks` did the most effective `Ad_Topic` generate?
- Compare the `Click` rate of 5 countries (`Country_Name`) with the highest `Avg_Income`
  With the `Click` rate of the 5 countries (`Country_Name`) with the lowest `Avg_Income`
- How much are the `Avg_Income` and `Internet_Usage` columns correlated?
- Are `Time_Spent`,`Avg_Income`, `Internet_Usage` or `Male` columns correlated with `Click`?


## Prediction

We want to predict the `Click` column.
Try to run a `LogisticRegression` Spark ML classifier.
Do not forget to convert categorical variables into numerical values
(label indexation or one-hot-encoding) and split the data into a training and testing set.


# TP 4.3 - Sales clickstream

The aim of this project is to perform an analysis of the
[E-Shop Clothing Online Dataset](https://www.kaggle.com/datasets/heninursafitri/clothi).

The dataset consists of a table containing sales records (one row for each sold item).

## Getting Started

Once downloaded, unzip the dataset and upload the csv file to HDFS.
Next, start a Jupyter notebook as in
[TP3 Getting Started](https://github.com/SergioSim/debian-hadoop/blob/master/tp/3/README.md#getting-started).

## Computing some statistics

- Load the dataset into a Spark Dataframe.
- Retrieve the number of rows.
- Drop the `year` column.
- Which `clothing model` has the highest `price`?
    - How many times was it sold in total?
    - How many times was it sold in the 4th `month`?
- Which `session` resulted in the highest total `price`?
- How many distinct `clothing models` in the `main category` 1 were sold during the 6th `month`?
- Which `month` contains the highest number of sales?
    - Find the 5 most popular `clothing models` during this month.
    - What percentage of total sales in this month do the 5 most popular `clothing models` account for?
    - To which `page` category do this `clothing models` belong to?
- Do `prices` change over time for `clothing models`?
- Compute the average `price` by `main category`.
    - Which `clothing models` in the 1st `main category` have a higher average price and are classed in the 2nd `price 2` category?

## Streaming

- start a Netcat server in the VM with:
  `nc -lk 9999`
- Create a Spark Structured Streaming Dataframe that reads from the Netcat server
- Manually copy/paste some of the first lines into the netcat server.
  Or alternatively, you can automate line insertion with something like:
  ```bash
  function slowcat(){ while read; do sleep .1; echo "$REPLY"; done; }
  cat e-shop\ clothing\ 2008.csv | slowcat | nc -lk 999
  ```
  Source: [stackoverflow](https://stackoverflow.com/a/46968824)
- Recompute the previous statistics while inserting more lines into the netcat server.

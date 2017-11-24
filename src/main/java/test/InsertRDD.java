package test;

import com.healthmarketscience.jackcess.impl.query.QueryImpl;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.apache.spark.api.java.*;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by lefou on 24/11/2017.
 */
public class InsertRDD {

    public static void main(String[] args){

        try {


            SparkSession spark = SparkSession
                    .builder()
                    .appName("Java Spark SQL basic example")
                    .master("local")
                    .getOrCreate();

            runBasicDataFrameExample(spark);

        } catch (Exception e) {
            e.printStackTrace();
        }
        /*JSONObject object = new JSONObject();


JavaRDD<Url> urlsRDD = spark.read()
  .textFile("/Users/karuturi/Downloads/log.txt")
  .javaRDD()
  .map(new Function<String, Url>() {
    @Override
    public Url call(String line) throws Exception {
      String[] parts = line.split("\\t");
      Url url = new Url();
      url.setValue(parts[0].replaceAll("[", ""));
      return url;
    }
  });


        JavaRDD<String> textFile = sc.textFile("hdfs://...");
        JavaRDD<QueryImpl.Row> rowRDD = textFile.map(RowFactory::create);
        List<StructField> fields = Arrays.asList(
                DataTypes.createStructField("line", DataTypes.StringType, true));
        StructType schema = DataTypes.createStructType(fields);
        DataFrame df = sqlContext.createDataFrame(rowRDD, schema);*/
    }

    private static void runBasicDataFrameExample(SparkSession spark) throws AnalysisException {
        // $example on:create_df$
        Dataset<Row> df = spark.read().json("C:/Users/lefou/Downloads/test.json");

        // Displays the content of the DataFrame to stdout
        df.show();
        // +----+-------+
        // | age|   name|
        // +----+-------+
        // |null|Michael|
        // |  30|   Andy|
        // |  19| Justin|
        // +----+-------+
        // $example off:create_df$

        // $example on:untyped_ops$
        // Print the schema in a tree format
        df.printSchema();
        // root
        // |-- age: long (nullable = true)
        // |-- name: string (nullable = true)

        // Select only the "name" column
        df.select("monster").show();


        JavaRDD<Row> rdd = df.javaRDD();
        System.out.println("test : "+rdd.toString());
        // +-------+
        // |   name|
        // +-------+
        // |Michael|
        // |   Andy|
        // | Justin|
        // +-------+

        // Select everybody, but increment the age by 1
        //df.select(col("name"), col("age").plus(1)).show();
        // +-------+---------+
        // |   name|(age + 1)|
        // +-------+---------+
        // |Michael|     null|
        // |   Andy|       31|
        // | Justin|       20|
        // +-------+---------+

        // Select people older than 21
        //df.filter(col("age").gt(21)).show();
        // +---+----+
        // |age|name|
        // +---+----+
        // | 30|Andy|
        // +---+----+

        // Count people by age
        /*df.groupBy("age").count().show();
        // +----+-----+
        // | age|count|
        // +----+-----+
        // |  19|    1|
        // |null|    1|
        // |  30|    1|
        // +----+-----+
        // $example off:untyped_ops$

        // $example on:run_sql$
        // Register the DataFrame as a SQL temporary view
        df.createOrReplaceTempView("people");

        Dataset<Row> sqlDF = spark.sql("SELECT * FROM people");
        sqlDF.show();
        // +----+-------+
        // | age|   name|
        // +----+-------+
        // |null|Michael|
        // |  30|   Andy|
        // |  19| Justin|
        // +----+-------+
        // $example off:run_sql$

        // $example on:global_temp_view$
        // Register the DataFrame as a global temporary view
        df.createGlobalTempView("people");

        // Global temporary view is tied to a system preserved database `global_temp`
        spark.sql("SELECT * FROM global_temp.people").show();
        // +----+-------+
        // | age|   name|
        // +----+-------+
        // |null|Michael|
        // |  30|   Andy|
        // |  19| Justin|
        // +----+-------+

        // Global temporary view is cross-session
        spark.newSession().sql("SELECT * FROM global_temp.people").show();
        // +----+-------+
        // | age|   name|
        // +----+-------+
        // |null|Michael|
        // |  30|   Andy|
        // |  19| Justin|
        // +----+-------+
        // $example off:global_temp_view$*/
    }


}

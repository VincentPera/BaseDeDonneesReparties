package test;

import com.healthmarketscience.jackcess.impl.query.QueryImpl;

import java.util.Arrays;

/**
 * Created by lefou on 24/11/2017.
 */
public class InsertRDD {

    public static void main(String[] args){
        JavaRDD<String> textFile = sc.textFile("hdfs://...");
        JavaRDD<QueryImpl.Row> rowRDD = textFile.map(RowFactory::create);
        List<StructField> fields = Arrays.asList(
                DataTypes.createStructField("line", DataTypes.StringType, true));
        StructType schema = DataTypes.createStructType(fields);
        DataFrame df = sqlContext.createDataFrame(rowRDD, schema);
    }

}

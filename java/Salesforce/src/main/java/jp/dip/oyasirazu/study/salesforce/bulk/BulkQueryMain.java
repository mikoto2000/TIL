package jp.dip.oyasirazu.study.salesforce.bulk;
import java.io.*;
import java.util.*;

import com.sforce.async.*;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.ws.ConnectionException;
import com.sforce.ws.ConnectorConfig;


public class BulkQueryMain {

    public static void main(String[] args)
        throws AsyncApiException, ConnectionException, IOException, InterruptedException {
        BulkQueryExample example = new BulkQueryExample();

        // 登録・更新情報を引数から取得
        String objectName = args[0];
        String query = args[1];
        String loginInfoPath = args[2];

        // ユーザー情報をプロパティファイルから取得
        Properties p = new Properties();
        p.load(new InputStreamReader(new FileInputStream(loginInfoPath), "UTF-8"));

        String userName = p.getProperty("username", "");
        String password = p.getProperty("password", "");

        String[] inputDataArray = {objectName, query, userName, password};
        System.out.println(String.join(", ", inputDataArray));

        if (userName.equals("") || password.equals("")) {
            System.exit(1);
        }

        example.runSample(objectName, userName, password, query);
    }
}

class BulkQueryExample {

    private PartnerConnection partnerConnection;

    /**
     * Creates a Bulk API job and query batches for a String.
     */
    public void runSample(String sobjectType, String userName,
            String password, String query)
        throws AsyncApiException, ConnectionException, IOException, InterruptedException {
        BulkConnection connection = getBulkConnection(userName, password);
        JobInfo job = createJob(sobjectType, connection);
        BatchInfo batchInfo = createBatchesFromString(connection, job, query);
        closeJob(connection, job.getId());
        String[] queryResults = awaitCompletion(connection, job, batchInfo);
        checkResults(connection, job, batchInfo, queryResults);
        partnerConnection.logout();
    }



    /**
     * Gets the results of the operation and checks for errors.
     */
    private void checkResults(BulkConnection connection, JobInfo job,
            BatchInfo batchInfo, String[] queryResults)
        throws AsyncApiException, IOException {
        for (String queryResult : queryResults) {
            CSVReader rdr =
                new CSVReader(connection.getQueryResultStream(job.getId(), batchInfo.getId(), queryResult));
            List<String> resultHeader = rdr.nextRecord();
            int resultCols = resultHeader.size();

            for (int i = 0; i < resultCols; i++) {
                System.out.print(resultHeader.get(i) + ",");
            }
            System.out.println();

            List<String> row;
            while ((row = rdr.nextRecord()) != null) {
                for (int i = 0; i < resultCols; i++) {
                    System.out.print(row.get(i) + ",");
                }
                System.out.println();
            }
        }
    }



    private void closeJob(BulkConnection connection, String jobId)
        throws AsyncApiException {
        JobInfo job = new JobInfo();
        job.setId(jobId);
        job.setState(JobStateEnum.Closed);
        connection.updateJob(job);
    }



    /**
     * Wait for a job to complete by polling the Bulk API.
     * 
     * @param connection
     *            BulkConnection used to check results.
     * @param jobInfo
     *            The job awaiting completion.
     * @param batchInfo
     *            Batche for this job.
     * @throws AsyncApiException
     */
    private String[] awaitCompletion(BulkConnection connection, JobInfo jobInfo,
            BatchInfo batchInfo)
        throws AsyncApiException, InterruptedException {
        for(int i=0; i<10000; i++) {
            Thread.sleep(i==0 ? 30 * 1000 : 30 * 1000); //30 sec
            batchInfo = connection.getBatchInfo(jobInfo.getId(),
                    batchInfo.getId());
            if (batchInfo.getState() == BatchStateEnum.Completed) {
                QueryResultList list =
                    connection.getQueryResultList(jobInfo.getId(),
                        batchInfo.getId());
                String[] queryResults = list.getResult();
                System.out.println("queryResults: " + String.join(", ", queryResults));
                return queryResults;
            } else if (batchInfo.getState() == BatchStateEnum.Failed) {
                System.out.println("-------------- failed ----------"
                    + batchInfo);
                break;
            } else {
                System.out.println("-------------- waiting ----------"
                    + batchInfo);
            }
        }
        return new String[]{};
    }



    /**
     * Create a new job using the Bulk API.
     * 
     * @param sobjectType
     *            The object type being loaded, such as "Account"
     * @param connection
     *            BulkConnection used to create the new job.
     * @return The JobInfo for the new job.
     * @throws AsyncApiException
     */
    private JobInfo createJob(String sobjectType, BulkConnection connection)
        throws AsyncApiException {
        JobInfo job = new JobInfo();
        job.setObject(sobjectType);
        job.setOperation(OperationEnum.query);
        job.setContentType(ContentType.CSV);
        job = connection.createJob(job);
        System.out.println(job);
        return job;
    }



    /**
     * Create the BulkConnection used to call Bulk API operations.
     */
    private BulkConnection getBulkConnection(String userName, String password)
        throws ConnectionException, AsyncApiException {
        ConnectorConfig partnerConfig = new ConnectorConfig();
        partnerConfig.setUsername(userName);
        partnerConfig.setPassword(password);
        partnerConfig.setAuthEndpoint("https://login.salesforce.com/services/Soap/u/34.0");
        // Creating the connection automatically handles login and stores
        // the session in partnerConfig
        partnerConnection = new PartnerConnection(partnerConfig);
        // When PartnerConnection is instantiated, a login is implicitly
        // executed and, if successful,
        // a valid session is stored in the ConnectorConfig instance.
        // Use this key to initialize a BulkConnection:
        ConnectorConfig config = new ConnectorConfig();
        config.setSessionId(partnerConfig.getSessionId());
        // The endpoint for the Bulk API service is the same as for the normal
        // SOAP uri until the /Soap/ part. From here it's '/async/versionNumber'
        String soapEndpoint = partnerConfig.getServiceEndpoint();
        String apiVersion = "34.0";
        String restEndpoint = soapEndpoint.substring(0, soapEndpoint.indexOf("Soap/"))
            + "async/" + apiVersion;
        config.setRestEndpoint(restEndpoint);
        // This should only be false when doing debugging.
        config.setCompression(true);
        // Set this to true to see HTTP requests and responses on stdout
        config.setTraceMessage(false);
        BulkConnection connection = new BulkConnection(config);
        return connection;
    }



    /**
     * Create and upload batches using a CSV file.
     * The file into the appropriate size batch files.
     * 
     * @param connection
     *            Connection to use for creating batches
     * @param jobInfo
     *            Job associated with new batches
     * @param query
     *            SOQL query.
     */
    private BatchInfo createBatchesFromString(BulkConnection connection,
            JobInfo jobInfo, String query)
        throws IOException, AsyncApiException {
        InputStream is = new ByteArrayInputStream(query.getBytes());

        return connection.createBatchFromStream(jobInfo, is);
    }

    /**
     * Create a batch by uploading the contents of the file.
     * This closes the output stream.
     * 
     * @param tmpOut
     *            The output stream used to write the CSV data for a single batch.
     * @param tmpFile
     *            The file associated with the above stream.
     * @param batchInfos
     *            The batch info for the newly created batch is added to this list.
     * @param connection
     *            The BulkConnection used to create the new batch.
     * @param jobInfo
     *            The JobInfo associated with the new batch.
     */
    private void createBatch(FileOutputStream tmpOut, File tmpFile,
            List<BatchInfo> batchInfos, BulkConnection connection, JobInfo jobInfo)
        throws IOException, AsyncApiException {
        tmpOut.flush();
        tmpOut.close();
        FileInputStream tmpInputStream = new FileInputStream(tmpFile);
        try {
            BatchInfo batchInfo =
                connection.createBatchFromStream(jobInfo, tmpInputStream);
            System.out.println(batchInfo);
            batchInfos.add(batchInfo);

        } finally {
            tmpInputStream.close();
        }
    }


}

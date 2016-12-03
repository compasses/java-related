package event.sqlexecute;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.LineInputStream;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import event.sqlexecute.SQLExecute.MariaDBSQLExecute;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by I311352 on 12/1/2016.
 */
@Service
public class SQLScriptExecuteService {
    private static final Logger logger = Logger.getLogger(SQLScriptExecuteService.class);
    private static String DEFAULT_SQL_SOURCE = "/sql/data.sql";
    private static boolean USER_CONFIG = false;

    private static final String DEFAULT_DELIMITER = ";";
    private static final String COMMENTS = "--";


    private List<String> sqlLine = new ArrayList<>();

    @Autowired
    private MariaDBSQLExecute mariaDBSQLExecute;

    public void runSQL() {
        if (!loadSQLScriptLine()) {
            logger.error("Load file fail...do nothing");
            return;
        }
        mariaDBSQLExecute.executeSQL(sqlLine);
    }

    private boolean loadSQLScriptLine() {
        BufferedReader bufferedReader = null;
        try {
            if (USER_CONFIG == true) {
                //inputStream = new FileInputStream(DEFAULT_SQL_SOURCE);
                bufferedReader = new BufferedReader(new FileReader(DEFAULT_SQL_SOURCE));
            } else {
                bufferedReader = new BufferedReader(new FileReader(this.getClass().getResource(DEFAULT_SQL_SOURCE).getFile()));
            }

            String line;
            StringBuffer stringBuffer = null;
            while ((line = bufferedReader.readLine()) != null) {
                logger.info("Got sql ..." + line);
                String trimLine = line.trim();
                if (trimLine.isEmpty() || trimLine.startsWith(COMMENTS)) {
                    continue;
                }

                if (stringBuffer == null) {
                    stringBuffer = new StringBuffer(64);
                }

                if (trimLine.endsWith(DEFAULT_DELIMITER)) {
                    stringBuffer.append(" ").append(trimLine);
                    sqlLine.add(stringBuffer.toString());
                    stringBuffer = null;
                } else {
                    stringBuffer.append(" ").append(trimLine);
                }
            }

            return true;
        } catch (IOException e) {
            logger.error("Error reading sql file...");
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                logger.error("close error " + e);
            }
        }

        return false;
    }

    public static void setDefaultSqlSource(String defaultSqlSource) {
        DEFAULT_SQL_SOURCE = defaultSqlSource;
        USER_CONFIG = true;
    }

//    private void runScript(final Connection conn, final Reader reader) throws SQLException, IOException {
//        StringBuffer command = null;
//
//        Table table = null;
//        try {
//            final LineNumberReader lineReader = new LineNumberReader(reader);
//            String line = null;
//            while ((line = lineReader.readLine()) != null) {
//                if (command == null) {
//                    command = new StringBuffer();
//                }
//
//                if (table == null) {
//                    table = new Table();
//                }
//
//                String trimmedLine = line.trim();
//
//                // Interpret SQL Comment & Some statement that are not executable
//                if (trimmedLine.startsWith("--")
//                        || trimmedLine.startsWith("//")
//                        || trimmedLine.startsWith("#")
//                        || trimmedLine.toLowerCase().startsWith("rem inserting into")
//                        || trimmedLine.toLowerCase().startsWith("set define off")) {
//
//                    // do nothing...
//                } else if (trimmedLine.endsWith(this.delimiter) ||
//                        trimmedLine.endsWith(PL_SQL_BLOCK_END_DELIMITER)) { // Line is end of statement
//
//                    // Append
//                    if (trimmedLine.endsWith(this.delimiter)) {
//                        command.append(line.substring(0, line.lastIndexOf(this.delimiter)));
//                        command.append(" ");
//                    } else if (trimmedLine.endsWith(PL_SQL_BLOCK_END_DELIMITER)) {
//                        command.append(line.substring
//                                (0, line.lastIndexOf(PL_SQL_BLOCK_END_DELIMITER)));
//                        command.append(" ");
//                    }
//
//                    Statement stmt = null;
//                    ResultSet rs = null;
//                    try {
//                        stmt = conn.createStatement();
//                        boolean hasResults = false;
//                        if (this.stopOnError) {
//                            hasResults = stmt.execute(command.toString());
//                        } else {
//                            try {
//                                stmt.execute(command.toString());
//                            } catch (final SQLException e) {
//                                e.fillInStackTrace();
//                                err.println("Error executing SQL Command: \"" +
//                                        command + "\"");
//                                err.println(e);
//                                err.flush();
//                                throw e;
//                            }
//                        }
//
//                        rs = stmt.getResultSet();
//                        if (hasResults && rs != null) {
//
//                            List<String> headerRow = new ArrayList<String>();
//                            List<List<String>> toupleList = new ArrayList<List<String>>();
//
//                            // Print & Store result column names
//                            final ResultSetMetaData md = rs.getMetaData();
//                            final int cols = md.getColumnCount();
//                            for (int i = 0; i < cols; i++) {
//                                final String name = md.getColumnLabel(i + 1);
//                                out.print(name + "\t");
//
//                                headerRow.add(name);
//                            }
//
//                            table.setHeaderRow(headerRow);
//
//                            out.println("");
//                            out.println(StringUtils.repeat("---------", md.getColumnCount()));
//                            out.flush();
//
//                            // Print & Store result rows
//                            while (rs.next()) {
//                                List<String> touple = new ArrayList<String>();
//                                for (int i = 1; i <= cols; i++) {
//                                    final String value = rs.getString(i);
//                                    out.print(value + "\t");
//
//                                    touple.add(value);
//                                }
//                                out.println("");
//
//                                toupleList.add(touple);
//                            }
//                            out.flush();
//
//                            table.setToupleList(toupleList);
//                            this.tableList.add(table);
//                            table = null;
//                        } else {
//                            sqlOutput.add(stmt.getUpdateCount() + " row(s) affected.");
//
//                            out.println(stmt.getUpdateCount() + " row(s) affected.");
//                            out.flush();
//                        }
//                        command = null;
//                    } finally {
//                        if (rs != null) {
//                            try {
//                                rs.close();
//                            } catch (final Exception e) {
//                                err.println("Failed to close result: " + e.getMessage());
//                                err.flush();
//                            }
//                        }
//                        if (stmt != null) {
//                            try {
//                                stmt.close();
//                            } catch (final Exception e) {
//                                err.println("Failed to close statement: " + e.getMessage());
//                                err.flush();
//                            }
//                        }
//                    }
//                } else if (trimmedLine.endsWith(PL_SQL_BLOCK_SPLIT_DELIMITER)) {
//                    command.append(line.substring
//                            (0, line.lastIndexOf(this.PL_SQL_BLOCK_SPLIT_DELIMITER)));
//                    command.append(" ");
//                } else { // Line is middle of a statement
//
//                    // Append
//                    command.append(line);
//                    command.append(" ");
//                }
//            }
//            if (!this.autoCommit) {
//                conn.commit();
//            }
//        } catch (final SQLException e) {
//            conn.rollback();
//            e.fillInStackTrace();
//            err.println("Error executing SQL Command: \"" + command + "\"");
//            err.println(e);
//            err.flush();
//            throw e;
//        } catch (final IOException e) {
//            e.fillInStackTrace();
//            err.println("Error reading SQL Script.");
//            err.println(e);
//            err.flush();
//            throw e;
//        }
//    }
}

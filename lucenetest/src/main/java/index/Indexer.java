package index;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by i311352 on 5/23/2017.
 */
public class Indexer {
    private IndexWriter writer;

    public Indexer(String indexDirectoryPath) throws IOException {
        File f = new File(indexDirectoryPath);

        Directory indexDirectory = FSDirectory.open(f.toPath());
        StandardAnalyzer analyzer = new StandardAnalyzer();

        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);


        writer = new IndexWriter(indexDirectory, config);
    }

    public void close() throws IOException {
        writer.close();
    }

    private Document getDocument(File file) throws IOException {
        Document document = new Document();
        FieldType type1 = new FieldType();

        type1.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        Field contentField = new Field(LuceneConstants.CONTENTS, new FileReader(file), type1);
        FieldType type = new FieldType();
        type.setStored(true);
        Field fileNameField = new Field(LuceneConstants.FILE_NAME,
                file.getName(), type);
        //index file path
        Field filePathField = new Field(LuceneConstants.FILE_PATH,
                file.getCanonicalPath(), type);

        document.add(contentField);
        document.add(fileNameField);
        document.add(filePathField);

        return document;
    }

    private void indexFile(File file) throws IOException {
        System.out.println("Indexing "+file.getCanonicalPath());
        //Document document = getDocument(file);
        addDoc(writer,"123", "123");
        //writer.addDocument(document);
    }

    private static void addDoc(IndexWriter w, String _keyword, String _keywordid) throws IOException
    {
        Document doc = new Document();
//        doc.add(new TextField("Keyword", _keyword, Field.Store.YES));
//        doc.add(new StringField("KeywordID", _keywordid, Field.Store.YES));
        doc.add(new DoubleDocValuesField("double", 1.22));
        //doc.add(new LongRangeField());
        w.addDocument(doc);
    }

    private void indexFile () {
        Document document = new Document();
    }

    public int createIndex(String dataDirPath, FileFilter filter)
            throws IOException {
        //get all files in the data directory
        File[] files = new File(dataDirPath).listFiles();

        for (File file : files) {
            if(!file.isDirectory()
                    && !file.isHidden()
                    && file.exists()
                    && file.canRead()
                    && filter.accept(file)
                    ){
                indexFile(file);
            }
        }
        return writer.numDocs();
    }
}

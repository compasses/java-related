package index;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

/**
 * Created by i311352 on 5/23/2017.
 */
public class Searcher {
    IndexSearcher indexSearcher;
    QueryParser parser;
    Query query;

    public Searcher(String indexDirectoryPath) throws IOException {
        Directory directory = FSDirectory.open((new File(indexDirectoryPath)).toPath());
        indexSearcher = new IndexSearcher(DirectoryReader.open(directory));
        parser = new QueryParser(LuceneConstants.CONTENTS, new StandardAnalyzer());
    }

    public TopDocs search(String searchQuery) throws IOException, ParseException {
        query = parser.parse(searchQuery);
        return indexSearcher.search(query, LuceneConstants.MAX_SEARCH);
    }

    public Document getDocument(ScoreDoc scoreDoc) throws IOException {
        return indexSearcher.doc(scoreDoc.doc);
    }

    public void close() throws IOException {
    }
}

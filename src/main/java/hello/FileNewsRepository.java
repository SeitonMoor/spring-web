package hello;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileNewsRepository implements NewsRepository {

    private String file;

    public FileNewsRepository(String file) {
        this.file = file;
    }

    @Override
    public List<News> readNews() throws IOException {
        Optional<InputStream> source = Optional.ofNullable(ClassLoader.getSystemResourceAsStream(file));

        List<News> news = new ArrayList<>();

        LineNumberReader reader = new LineNumberReader(
                new InputStreamReader(source.orElseThrow(() -> new RuntimeException("No such resources"))));

        String string = reader.readLine();

        News oneNews = null;
        while (string != null) {
            if(string.isEmpty()) {
                if(oneNews != null) {
                    news.add(oneNews);
                    oneNews = null;
                }
            } else if(oneNews == null) {
                oneNews = new News(string);
            } else {
                oneNews.addLine(string);
            }
            string = reader.readLine();
        }
        if(oneNews != null)
            news.add(oneNews);

        return news;
    }
}
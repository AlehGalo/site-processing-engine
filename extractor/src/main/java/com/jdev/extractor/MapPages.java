package com.jdev.extractor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.TeeOutputStream;

import com.jdev.crawler.observer.IObserver;
import com.jdev.domain.Information;
import com.jdev.domain.adapter.ContentAdapter;
import com.jdev.domain.adapter.IContent;
import com.jdev.extractor.extractor.IExtractor;
import com.jdev.extractor.extractor.common.Analyser;
import com.jdev.extractor.extractor.common.MailExtractor;
import com.jdev.extractor.extractor.social.SocialAnalyser;
import com.jdev.report.InformationReport;
import com.jdev.report.SocialReport;
import com.jdev.report.StatisticsReport;
import com.jdev.report.XlsInformationReport;
import com.jdev.spider.core.Spider;

/**
 * @author Aleh
 */
public class MapPages {

    private static class Listener implements IObserver<IContent> {

        private final Analyser analyser = new Analyser();

        private final Information information;

        public Listener(final Information inf) {
            information = inf;
        }

        @Override
        public void onAction(final IContent event) {
            analyser.analyse(event, information);
        }

        public void addExtractor(final IExtractor<Set<String>, IContent> extractor) {
            analyser.addExtractor(extractor);
        }
    }

    /**
     * @param args
     * @throws Exception
     */
    public static final void main(final String[] args) throws Exception {
        final PrintStream stdOut = System.out;
        final PrintStream fileOutputFork = new PrintStream(new File("output.txt"));
        final PrintStream tee = new PrintStream(new TeeOutputStream(fileOutputFork, stdOut));

        System.setOut(tee);

        final String file = args[0];

        final List<String> urls = FileUtils.readLines(new File(file), Charset.forName("UTF-8"));

        for (final String url : urls) {
            System.out.println(url);

            final Information inf = new Information(url);
            final Spider spider = new Spider(inf);
            final Listener listener = new Listener(inf);
            listener.addExtractor(new MailExtractor());

            spider.addListener(listener);

            spider.collect();

            new InformationReport().writeReportIntoStream(inf, System.out);
            fillSocialToBean(spider, inf);
            new SocialReport().writeReportIntoStream(inf, System.out);
            new StatisticsReport().writeReportIntoStream(spider.getStatistics(), System.out);
            new XlsInformationReport().writeReportIntoStream(inf, new FileOutputStream(new File(inf
                    .getHomeUrl().getHost() + "_" + System.currentTimeMillis() + ".xlsx")));
        }
    }

    //
    // /**
    // * @param args
    // * @throws Exception
    // */
    // public static final void main(final String[] args) throws Exception {
    // if (ArrayUtils.isEmpty(args)) {
    // throw new IllegalArgumentException("No properties files are provided");
    // }
    // final PrintStream stdOut = System.out;
    // final PrintStream fileOutputFork = new PrintStream(new
    // File("output.txt"));
    // final PrintStream tee = new PrintStream(new
    // TeeOutputStream(fileOutputFork, stdOut));
    //
    // System.setOut(tee);
    //
    // String file = args[0];
    // List<TargetWebSite> websites = read(file);
    //
    // String csvOutputFile = "output_" + System.currentTimeMillis() + ".csv";
    // OutputStream csvStream = FileUtils.openOutputStream(new
    // File(csvOutputFile));
    // CsvReport csvReport = new CsvReport(csvStream);
    //
    // csvReport.writeHeaders();
    //
    // for (final TargetWebSite website : websites) {
    // String url = website.getUrl();
    // System.out.println(url);
    //
    // final Information inf = new Information(url);
    // final Spider spider = new Spider(inf);
    // final Listener listener = new Listener(inf);
    // listener.addExtractor(new MailExtractor());
    // spider.addListener(listener);
    //
    // spider.collect(50);
    //
    // new InformationReport().writeReportIntoStream(inf, System.out);
    //
    // fillSocialToBean(spider, inf);
    // new SocialReport().writeReportIntoStream(inf, System.out);
    // csvReport.addLine(website, inf);
    //
    // new StatisticsReport().writeReportIntoStream(spider.getStatistics(),
    // System.out);
    // new XlsInformationReport().writeReportIntoStream(inf, new
    // FileOutputStream(new File(inf
    // .getHomeUrl().getHost() + "_" + System.currentTimeMillis() + ".xlsx")));
    //
    // System.out.println("DONE WITH " + url);
    // System.out.println();
    // System.out.println();
    // }
    //
    // csvReport.close();
    // }
    //
    // private static List<TargetWebSite> read(final String file) throws
    // IOException {
    // List<String> urls = FileUtils.readLines(new File(file),
    // Charset.forName("UTF-8"));
    //
    // List<TargetWebSite> res = new ArrayList<>(urls.size());
    //
    // for (String line : urls) {
    // String[] split = line.split("\t");
    // res.add(new TargetWebSite(split[0], split[1], split[2]));
    // }
    //
    // return res;
    // }

    /**
     * @param spider
     */
    private static void fillSocialToBean(final Spider spider, final Information inf) {
        SocialAnalyser socialAnalyser = new SocialAnalyser();
        for (final IContent s : spider.getSetOfNotDomainedUrls()) {
            final IContent innerUrl = s;
            socialAnalyser.analyse(new ContentAdapter() {

                @Override
                public String getUrl() {
                    return innerUrl.getUrl();
                }
            }, inf);
        }
    }
}

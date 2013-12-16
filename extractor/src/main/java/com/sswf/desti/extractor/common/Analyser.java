/**
 * 
 */
package com.sswf.desti.extractor.common;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import com.sswf.desti.analyser.AbstractAnalyser;
import com.sswf.desti.domain.Information;
import com.sswf.desti.domain.adapter.IContent;
import com.sswf.desti.domain.mail.IMail;
import com.sswf.desti.domain.mail.Mail;
import com.sswf.desti.extractor.IExtractor;

/**
 * @author Aleh
 * 
 */
public class Analyser extends AbstractAnalyser {

    /**
     * 
     */
    public Analyser() {
        addExtractor(new MailExtractor());
        addExtractor(new PhoneExtractor());
        addExtractor(new PriceExtractor());
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.sswf.desti.analyser.IAnalyse#analyse(com.sswf.desti.spider.core.url
     * .IUrl, org.springframework.aop.IntroductionInfo)
     */
    @Override
    public void analyse(final IContent url, final Information i) {
        if (url != null && i != null) {
            for (final IExtractor<Set<String>, IContent> element : setOfExtractors) {
                final Set<String> setResult = element.extract(url);
                if (CollectionUtils.isNotEmpty(setResult)) {
                    if (element instanceof MailExtractor) {
                        i.getMails().addAll(getMails(setResult));
                    } else if (element instanceof PhoneExtractor) {
                        i.getPhoneNumbers().addAll(setResult);
                    } else if (element instanceof PriceExtractor) {
                        i.getSetOfPricesTemp().addAll(setResult);
                    }
                }
            }
        }
    }

    /**
     * @param source
     * @return
     */
    private Set<IMail> getMails(final Set<String> source) {
        final Set<IMail> res = new HashSet<>();
        if (CollectionUtils.isNotEmpty(source)) {
            for (final String string : source) {
                res.add(new Mail(string));
            }
        }
        return res;
    }
}

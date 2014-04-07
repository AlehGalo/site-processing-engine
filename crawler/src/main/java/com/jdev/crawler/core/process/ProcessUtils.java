package com.jdev.crawler.core.process;

import java.util.Arrays;
import java.util.Collections;

import com.jdev.crawler.core.process.container.ConditionalProcess;
import com.jdev.crawler.core.process.container.ProcessChain;
import com.jdev.crawler.core.process.container.ProcessChoice;
import com.jdev.crawler.core.process.container.ProcessDoWhile;
import com.jdev.crawler.core.process.container.ProcessForEach;
import com.jdev.crawler.core.process.container.ProcessParallel;
import com.jdev.crawler.core.process.handler.MimeType;
import com.jdev.crawler.core.request.IRequestBuilder;
import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.RequestReservedWord;
import com.jdev.crawler.core.step.HTTPMethod;
import com.jdev.crawler.core.step.IStepConfig;
import com.jdev.crawler.core.step.StepConfigAdapter;
import com.jdev.crawler.core.step.validator.DummyValidator;
import com.jdev.crawler.core.step.validator.IValidator;

/**
 * @author Aleh
 * 
 */
public final class ProcessUtils {

    private ProcessUtils() {
    }

    public static IProcess process(final IStepConfig config) {
        return new SimpleStepProcess(config, Collections.<IProcessResultHandler> emptyList());
    }

    public static IProcess process(final IStepConfig config,
            final IProcessResultHandler... handlers) {
        return new SimpleStepProcess(config, Arrays.asList(handlers));
    }

    public static IProcess assemble(final IStepConfig config) {
        return new AssembledStepProcess(config, Collections.<IProcessResultHandler> emptyList());
    }

    public static IProcess assemble(final IStepConfig config, final IRequestBuilder requestBuilder) {
        return new AssembledStepProcess(config, Collections.<IProcessResultHandler> emptyList(),
                requestBuilder);
    }

    public static IProcess waitUntilValidatoIsTrue(final IStepConfig config,
            final IValidator validator) {
        final AssembledStepProcess process = new AssembledStepProcess(config,
                Collections.<IProcessResultHandler> emptyList());
        process.setValidator(validator);
        return process;
    }

    public static IProcess assemble(final IStepConfig config,
            final IProcessResultHandler... handlers) {
        return new AssembledStepProcess(config, Arrays.asList(handlers));
    }

    public static IProcess optAssemble(final IStepConfig config, final IValidator validator) {
        final OptionalAssembledStepProcessor processor = new OptionalAssembledStepProcessor(config,
                Collections.<IProcessResultHandler> emptyList());
        processor.setValidator(validator);
        return processor;
    }

    public static IProcess handlersOnly(final IProcessResultHandler... handlers) {
        return new HandlersOnlyStepProcess("", handlers);
    }

    public static IProcess optAssemble(final IStepConfig config) {
        final OptionalAssembledStepProcessor processor = new OptionalAssembledStepProcessor(config,
                Collections.<IProcessResultHandler> emptyList());
        processor.setValidator(new DummyValidator());
        return processor;
    }

    public static IProcess chain(final IProcess... elements) {
        return new ProcessChain<IProcess>(Arrays.asList(elements));
    }

    public static IProcess chain(final IStepConfig... elements) {
        final IProcess[] va = new IProcess[elements.length];
        for (int i = 0; i < elements.length; i++) {
            va[i] = new SimpleStepProcess(elements[i],
                    Collections.<IProcessResultHandler> emptyList());
        }
        return chain(va);
    }

    public static IProcess parallel(final IProcess... elements) {
        return new ProcessParallel<IProcess>(Arrays.asList(elements));
    }

    public static IProcess choice(final IConditionalProcess... elements) {
        return new ProcessChoice<IConditionalProcess>(Arrays.asList(elements));
    }

    public static IProcess multi(final RequestReservedWord word, final IStepConfig stepConfig,
            final IProcess process) {
        return new UnlimitedMultiStepProcess(stepConfig,
                Collections.<IProcessResultHandler> emptyList(), process, word);
    }

    public static IProcess multi(final RequestReservedWord word, final IStepConfig stepConfig) {
        return new UnlimitedMultiStepProcess(stepConfig,
                Collections.<IProcessResultHandler> emptyList(), null, word);
    }

    public static IProcess multi(final RequestReservedWord word, final IStepConfig stepConfig,
            final IProcess process, final IProcessResultHandler... handlers) {
        return new UnlimitedMultiStepProcess(stepConfig, Arrays.asList(handlers), process, word);
    }

    public static IProcess multi(final RequestReservedWord word, final IStepConfig stepConfig,
            final IProcessResultHandler... handlers) {
        return new UnlimitedMultiStepProcess(stepConfig, Arrays.asList(handlers), null, word);
    }

    public static IProcess multi(final IStepConfig stepConfig, final IProcess process,
            final IProcessResultHandler... handlers) {
        return new UnlimitedMultiStepProcess(stepConfig, Arrays.asList(handlers), process);
    }

    public static IProcess multi(final IStepConfig stepConfig,
            final IProcessResultHandler... handlers) {
        return new UnlimitedMultiStepProcess(stepConfig, Arrays.asList(handlers), null);
    }

    public static IProcess multi(final IStepConfig stepConfig, final IProcess process) {
        return new UnlimitedMultiStepProcess(stepConfig,
                Collections.<IProcessResultHandler> emptyList(), process);
    }

    public static IProcess multiLimited(final RequestReservedWord word,
            final IStepConfig stepConfig, final IProcess process, final int limit,
            final IProcessResultHandler... handlers) {
        return new LimitedMultiStepProcess(stepConfig, Arrays.asList(handlers), null, process,
                limit, word);
    }

    public static IProcess multiLimited(final RequestReservedWord word,
            final IStepConfig stepConfig, final IProcess process, final int limit) {
        return new LimitedMultiStepProcess(stepConfig,
                Collections.<IProcessResultHandler> emptyList(), null, process, limit, word);
    }

    public static IProcess multiLimitedToThree(final RequestReservedWord word,
            final IStepConfig stepConfig, final IProcess process) {
        return multiLimited(word, stepConfig, process, 3);
    }

    public static IProcess multiLimitedToThree(final RequestReservedWord word,
            final IStepConfig stepConfig, final IProcess process,
            final IProcessResultHandler... handlers) {
        return multiLimited(word, stepConfig, process, 3, handlers);
    }

    public static IProcess multiLimitedToThree(final IStepConfig stepConfig,
            final IProcess process, final IProcessResultHandler... handlers) {
        return multiLimited(null, stepConfig, process, 3, handlers);
    }

    public static IConditionalProcess choice(final IValidator validator, final IStepConfig step) {
        return new ConditionalSingleStepProcess(validator, step);
    }

    public static IConditionalProcess choice(final IValidator validator, final IProcess process) {
        return new ConditionalProcess(validator, process);
    }

    public static IProcess doGet(final String url) {
        return new SimpleStepProcess(new StepConfigAdapter() {
            @Override
            public String getUrl() {
                return url;
            }
        }, Collections.<IProcessResultHandler> emptyList());
    }

    public static IProcess doGet(final String url, final IProcessResultHandler... handlers) {
        return new SimpleStepProcess(new StepConfigAdapter() {
            @Override
            public String getUrl() {
                return url;
            }
        }, Arrays.asList(handlers));
    }

    public static IProcess doGet(final String url, final MimeType[] acceMimeTypes,
            final IProcessResultHandler... handlers) {
        SimpleStepProcess process = new SimpleStepProcess(new StepConfigAdapter() {
            @Override
            public String getUrl() {
                return url;
            }
        }, Arrays.asList(handlers));
        process.setAcceptedTypes(acceMimeTypes);
        return process;
    }

    public static IProcess doPost(final String url) {
        return new SimpleStepProcess(new StepConfigAdapter() {

            @Override
            public String getUrl() {
                return url;
            }

            @Override
            public HTTPMethod getMethod() {
                return HTTPMethod.POST;
            }
        }, Collections.<IProcessResultHandler> emptyList());
    }

    /**
     * @param process
     * @return process for work with.
     */
    public static IProcess doWhile(final IConditionalProcess process) {
        return new ProcessDoWhile<IConditionalProcess>(Arrays.asList(process));
    }

    /**
     * @param selector
     * @param elements
     * @return
     */
    public static IProcess forEach(final ISelector<String> selector, final IProcess... elements) {
        return new ProcessForEach<IProcess>(Arrays.asList(elements), selector);
    }
}
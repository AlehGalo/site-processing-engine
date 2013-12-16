package com.jdev.crawler.core.process;

import java.util.Arrays;
import java.util.Collections;

import com.jdev.crawler.core.process.handler.MimeType;
import com.jdev.crawler.core.request.IRequestBuilder;
import com.jdev.crawler.core.selector.ISelector;
import com.jdev.crawler.core.selector.RequestReservedWord;
import com.jdev.crawler.core.step.DummyValidator;
import com.jdev.crawler.core.step.IStepConfig;
import com.jdev.crawler.core.step.IValidator;
import com.jdev.crawler.core.step.StepConfigAdapter;

public final class ProcessUtils {
    private ProcessUtils() {
    }

    public static IProcess process(IStepConfig config) {
        return new SimpleStepProcess(config, Collections.<IProcessResultHandler> emptyList());
    }

    public static IProcess process(IStepConfig config, IProcessResultHandler... handlers) {
        return new SimpleStepProcess(config, Arrays.asList(handlers));
    }

    public static IProcess assemble(IStepConfig config) {
        return new AssembledStepProcess(config, Collections.<IProcessResultHandler> emptyList());
    }

    public static IProcess login(IStepConfig config) {
        return new LoginStepProcess(config, Collections.<IProcessResultHandler> emptyList());
    }

    public static IProcess login(IStepConfig config, IProcessResultHandler... handlers) {
        return new LoginStepProcess(config, Arrays.asList(handlers));
    }

    public static IProcess assemble(IStepConfig config, IRequestBuilder requestBuilder) {
        return new AssembledStepProcess(config, Collections.<IProcessResultHandler> emptyList(),
                requestBuilder);
    }

    public static IProcess assemble(IStepConfig config, IValidator validator) {
        final AssembledStepProcess process = new AssembledStepProcess(config,
                Collections.<IProcessResultHandler> emptyList());
        process.setValidator(validator);
        return process;
    }

    public static IProcess assemble(IStepConfig config, IProcessResultHandler... handlers) {
        return new AssembledStepProcess(config, Arrays.asList(handlers));
    }

    public static IProcess optAssemble(IStepConfig config, IValidator validator) {
        final OptionalAssembledStepProcessor processor = new OptionalAssembledStepProcessor(config,
                Collections.<IProcessResultHandler> emptyList());
        processor.setValidator(validator);
        return processor;
    }

    public static IProcess handlersOnly(IProcessResultHandler... handlers) {
        return new HandlersOnlyStepProcess("", handlers);
    }

    public static IProcess optAssemble(IStepConfig config) {
        final OptionalAssembledStepProcessor processor = new OptionalAssembledStepProcessor(config,
                Collections.<IProcessResultHandler> emptyList());
        processor.setValidator(new DummyValidator());
        return processor;
    }

    public static IProcess chain(IProcess... elements) {
        return new ProcessChain(Arrays.asList(elements));
    }

    public static IProcess chain(IStepConfig... elements) {
        final IProcess[] va = new IProcess[elements.length];
        for (int i = 0; i < elements.length; i++) {
            va[i] = new SimpleStepProcess(elements[i],
                    Collections.<IProcessResultHandler> emptyList());
        }
        return chain(va);
    }

    public static IProcess forEach(ISelector selector, IProcess process) {
        return new ForEachProcess(selector, process);
    }

    public static IProcess parallel(IProcess... elements) {
        return new ProcessParallel(Arrays.asList(elements));
    }

    public static IProcess choice(IConditionalProcess... elements) {
        return new ProcessChoice(Arrays.asList(elements));
    }

    public static IProcess multi(RequestReservedWord word, final IStepConfig stepConfig,
            IProcess process) {
        return new UnlimitedMultiStepProcess(stepConfig,
                Collections.<IProcessResultHandler> emptyList(), process, word);
    }

    public static IProcess multi(RequestReservedWord word, final IStepConfig stepConfig) {
        return new UnlimitedMultiStepProcess(stepConfig,
                Collections.<IProcessResultHandler> emptyList(), null, word);
    }

    public static IProcess multi(RequestReservedWord word, final IStepConfig stepConfig,
            IProcess process, IProcessResultHandler... handlers) {
        return new UnlimitedMultiStepProcess(stepConfig, Arrays.asList(handlers), process, word);
    }

    public static IProcess multi(RequestReservedWord word, final IStepConfig stepConfig,
            IProcessResultHandler... handlers) {
        return new UnlimitedMultiStepProcess(stepConfig, Arrays.asList(handlers), null, word);
    }

    public static IProcess multi(final IStepConfig stepConfig, IProcess process,
            IProcessResultHandler... handlers) {
        return new UnlimitedMultiStepProcess(stepConfig, Arrays.asList(handlers), process);
    }

    public static IProcess multi(final IStepConfig stepConfig, IProcessResultHandler... handlers) {
        return new UnlimitedMultiStepProcess(stepConfig, Arrays.asList(handlers), null);
    }

    public static IProcess multi(final IStepConfig stepConfig, IProcess process) {
        return new UnlimitedMultiStepProcess(stepConfig,
                Collections.<IProcessResultHandler> emptyList(), process);
    }

    public static IProcess multiLimited(RequestReservedWord word, final IStepConfig stepConfig,
            IProcess process, int limit, IProcessResultHandler... handlers) {
        return new LimitedMultiStepProcess(stepConfig, Arrays.asList(handlers), null, process,
                limit, word);
    }

    public static IProcess multiLimited(RequestReservedWord word, final IStepConfig stepConfig,
            IProcess process, int limit) {
        return new LimitedMultiStepProcess(stepConfig,
                Collections.<IProcessResultHandler> emptyList(), null, process, limit, word);
    }

    public static IProcess multiLimitedToThree(RequestReservedWord word,
            final IStepConfig stepConfig, IProcess process) {
        return multiLimited(word, stepConfig, process, 3);
    }

    public static IProcess multiLimitedToThree(RequestReservedWord word,
            final IStepConfig stepConfig, IProcess process, IProcessResultHandler... handlers) {
        return multiLimited(word, stepConfig, process, 3, handlers);
    }

    public static IProcess multiLimitedToThree(final IStepConfig stepConfig, IProcess process,
            IProcessResultHandler... handlers) {
        return multiLimited(null, stepConfig, process, 3, handlers);
    }

    public static IConditionalProcess choice(IValidator validator, IStepConfig step) {
        return new ConditionalSingleStepProcess(validator, step);
    }

    public static IConditionalProcess choice(IValidator validator, IProcess process) {
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

    public static IProcess doGet(final String url, IProcessResultHandler... handlers) {
        return new SimpleStepProcess(new StepConfigAdapter() {
            @Override
            public String getUrl() {
                return url;
            }
        }, Arrays.asList(handlers));
    }

    public static IProcess doGet(final String url, MimeType[] acceMimeTypes,
            IProcessResultHandler... handlers) {
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
            public String getMethod() {
                return "POST";
            }
        }, Collections.<IProcessResultHandler> emptyList());
    }
}

package com.jdev.crawler.core.process;

import static java.util.Arrays.asList;

import java.util.Arrays;

import com.jdev.crawler.core.process.container.ConditionalProcess;
import com.jdev.crawler.core.process.container.ProcessChain;
import com.jdev.crawler.core.process.container.ProcessChoice;
import com.jdev.crawler.core.process.container.ProcessDoWhile;
import com.jdev.crawler.core.process.container.ProcessForEachBlock;
import com.jdev.crawler.core.process.container.ProcessParallel;
import com.jdev.crawler.core.process.handler.MimeType;
import com.jdev.crawler.core.process.handler.ProcessResultHandlerChain;
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

    // Simple get/post methods
    public static IProcess doGet(final String url) {
        return new SimpleStepProcess(new StepConfigAdapter() {
            @Override
            public String getUrl() {
                return url;
            }
        });
    }

    public static IProcess doGet(final String url, final IProcessResultHandler handlers) {
        return new SimpleStepProcess(new StepConfigAdapter() {
            @Override
            public String getUrl() {
                return url;
            }
        }, handlers);
    }

    public static IProcess doGet(final String url, final MimeType[] acceMimeTypes,
            final IProcessResultHandler handlers) {
        SimpleStepProcess process = new SimpleStepProcess(new StepConfigAdapter() {
            @Override
            public String getUrl() {
                return url;
            }
        }, handlers);
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
        });
    }

    public static IProcess process(final IStepConfig config) {
        return new SimpleStepProcess(config);
    }

    public static IProcess process(final IStepConfig config, final IProcessResultHandler handlers) {
        return new SimpleStepProcess(config, handlers);
    }

    public static IProcess assemble(final IStepConfig config) {
        return new AssembledStepProcess(config);
    }

    public static IProcess assemble(final IStepConfig config, final IRequestBuilder requestBuilder) {
        return new AssembledStepProcess(config, null, requestBuilder);
    }

    public static IProcess waitUntilValidatoIsTrue(final IStepConfig config,
            final IValidator validator) {
        final AssembledStepProcess process = new AssembledStepProcess(config);
        process.setValidator(validator);
        return process;
    }

    public static IProcess assemble(final IStepConfig config, final IProcessResultHandler handlers) {
        return new AssembledStepProcess(config, handlers);
    }

    public static IProcess optAssemble(final IStepConfig config, final IValidator validator) {
        final OptionalAssembledStepProcessor processor = new OptionalAssembledStepProcessor(config);
        processor.setValidator(validator);
        return processor;
    }

    public static IProcess handlersOnly(final IProcessResultHandler... handlers) {
        return new HandlersOnlyStepProcess("", handlers);
    }

    public static IProcess optAssemble(final IStepConfig config) {
        final OptionalAssembledStepProcessor processor = new OptionalAssembledStepProcessor(config);
        processor.setValidator(new DummyValidator());
        return processor;
    }

    public static IProcess chain(final IProcess... elements) {
        return new ProcessChain<IProcess>(Arrays.asList(elements));
    }

    public static IProcess chain(final IStepConfig... elements) {
        final IProcess[] va = new IProcess[elements.length];
        for (int i = 0; i < elements.length; i++) {
            va[i] = new SimpleStepProcess(elements[i]);
        }
        return chain(va);
    }

    public static IProcess parallel(final IProcess... elements) {
        return new ProcessParallel<IProcess>(Arrays.asList(elements));
    }

    public static IProcess choice(final IConditionalProcess... elements) {
        return new ProcessChoice<IConditionalProcess>(Arrays.asList(elements));
    }

    public static IProcess multiLimited(final RequestReservedWord word,
            final IStepConfig stepConfig, final IProcess process, final int limit,
            final IProcessResultHandler handler) {
        return new AssembledMultiLimitedStepProcess(stepConfig, handler, limit);
    }

    public static IProcess multiLimitedToThree(final RequestReservedWord word,
            final IStepConfig stepConfig, final IProcess process,
            final IProcessResultHandler handlers) {
        return multiLimited(word, stepConfig, process, 3, handlers);
    }

    public static IProcess multiLimitedToThree(final IStepConfig stepConfig,
            final IProcess process, final IProcessResultHandler handlers) {
        return multiLimited(null, stepConfig, process, 3, handlers);
    }

    public static IConditionalProcess choice(final IValidator validator, final IStepConfig step) {
        return new ConditionalSingleStepProcess(validator, step);
    }

    public static IConditionalProcess choice(final IValidator validator, final IProcess process) {
        return new ConditionalProcess(validator, process);
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
    public static IProcess forEachBlock(final ISelector<String> selector,
            final IProcess... elements) {
        return new ProcessForEachBlock<IProcess>(asList(elements), selector);
    }

    /**
     * @param selector
     * @param elements
     * @return
     */
    public static IProcess multi(final IStepConfig config, final IProcessResultHandler handler) {
        return new AssembledMultiStepProcess(config, handler);
    }

    /**
     * @param selector
     * @param elements
     * @return
     */
    public static IProcess skipKnownError(final IProcess process, final String message) {
        return new SkipErrorStepProcess(process, message);
    }

    /**
     * Creates multi chain processor that processes list of process result
     * handlers.
     * 
     * @param processResultHandler
     *            array of handlers.
     * @return chained process result handler.
     */
    public static IProcessResultHandler createProcessResultHandlerChain(
            final IProcessResultHandler... processResultHandler) {
        return new ProcessResultHandlerChain(Arrays.asList(processResultHandler));
    }
}
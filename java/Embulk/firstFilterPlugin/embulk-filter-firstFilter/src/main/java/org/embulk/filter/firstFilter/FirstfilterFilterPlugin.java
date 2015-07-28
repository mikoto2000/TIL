package org.embulk.filter.firstFilter;

import com.google.common.base.Optional;
import org.embulk.config.Config;
import org.embulk.config.ConfigDefault;
import org.embulk.config.ConfigDiff;
import org.embulk.config.ConfigSource;
import org.embulk.config.Task;
import org.embulk.config.TaskSource;
import org.embulk.spi.Column;
import org.embulk.spi.Exec;
import org.embulk.spi.FilterPlugin;
import org.embulk.spi.Page;
import org.embulk.spi.PageBuilder;
import org.embulk.spi.PageOutput;
import org.embulk.spi.PageReader;
import org.embulk.spi.Schema;
import org.embulk.spi.type.Types;

public class FirstfilterFilterPlugin
        implements FilterPlugin
{
    public interface PluginTask
            extends Task
    { }

    @Override
    public void transaction(ConfigSource config, Schema inputSchema,
            FilterPlugin.Control control)
    {
        System.out.println("transaction!");
        System.out.print("config: ");
        System.out.println(config);
        System.out.print("inputSchema: ");
        System.out.println(inputSchema);
        System.out.print("control: ");
        System.out.println(control);
        PluginTask task = config.loadConfig(PluginTask.class);

        java.util.List<Column> newSchemaColumns = inputSchema.getColumns();
        Schema.Builder builder = Schema.builder();

        // カラムを追加
        for (Column column : newSchemaColumns) {
            builder.add(column.getName(), column.getType());
        }
        builder.add("additional", Types.STRING);

        Schema outputSchema = builder.build();

        control.run(task.dump(), outputSchema);
    }

    @Override
    public PageOutput open(TaskSource taskSource, Schema inputSchema,
            Schema outputSchema, PageOutput output)
    {
        System.out.println("open!");
        System.out.print("taskSource: ");
        System.out.println(taskSource);
        System.out.print("inputSchema: ");
        System.out.println(inputSchema);
        System.out.print("outputSchema: ");
        System.out.println(outputSchema);
        System.out.print("output: ");
        System.out.println(output);
        PluginTask task = taskSource.loadTask(PluginTask.class);
        System.out.print("task: ");
        System.out.println(task);

        return new MyPageOutput(output);
    }
}

class MyPageOutput implements PageOutput {
    private PageOutput originalPageOutput;

    public MyPageOutput(PageOutput originalPageOutput) {
        this.originalPageOutput = originalPageOutput;
    }

    @Override
    public void add(Page page) {
        originalPageOutput.add(page);
    }

    @Override
    public void close() {
        originalPageOutput.close();
    }

    @Override
    public void finish() {
        originalPageOutput.finish();
    }
}
